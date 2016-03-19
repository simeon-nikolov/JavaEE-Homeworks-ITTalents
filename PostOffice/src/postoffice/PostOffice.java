package postoffice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PostOffice implements LetterSendable, Runnable, Iterable<PostBox> {
	private static final float PERCENT = 100.0f;
	public static final int POST_OBJECTS_THRESHOLD = 50;
	private static final int NUMBER_OF_POST_BOXES = 50;
	
	private List<Postman> postmen;
	private List<PostBox> postBoxes;
	private List<PostObject> repository;
	private Map<Date, List<PostObject>> archive;
	
	public PostOffice() {
		this.postmen = new ArrayList<Postman>();
		this.postBoxes = new ArrayList<PostBox>();
		this.repository = new ArrayList<PostObject>();
		this.archive = new TreeMap<Date, List<PostObject>>((d1, d2) -> d1.compareTo(d2));
		
		// generate 50 post boxes
		for (int boxNumber = 1; boxNumber <= NUMBER_OF_POST_BOXES; boxNumber++) {
			PostBox postBox = new PostBox(boxNumber);
			this.postBoxes.add(postBox);
		}
	}
	
	public void registerPostman(Postman postman) {
		if (postman != null) {
			this.postmen.add(postman);
		}
	}
	
	public void addPostObject(PostObject postObject) {
		if (postObject != null) {
			synchronized (this.repository) {
				this.repository.add(postObject);
				this.repository.notifyAll();
				
				synchronized (this.archive) {
					Date today = getOnlyDate(new Date());
					
					if (!this.archive.containsKey(today)) {
						List<PostObject> postObjects = new LinkedList<PostObject>();
						this.archive.put(today, postObjects);
					}
					
					List<PostObject> archiveForToday = this.archive.get(today);
					archiveForToday.add(postObject);
				}
			}
		}
	}

	private Date getOnlyDate(Date date) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	public void addLetters(List<Letter> letters) {
		if (letters != null) {
			for (Letter letter : letters) {
				this.addPostObject(letter);
			}
		}
	}
	
	@Override
	public void sendLetter(Letter letter) {
		this.addPostObject(letter);
	}
	
	public PostBox getRandomPostBox() {
		PostBox postBox = this.postBoxes.get((int)(Math.random() * this.postBoxes.size()));
		return postBox;
	}
	
	public int getRepositorySize() {
		synchronized (this.repository) {
			return this.repository.size();
		}
	}
	
	public List<PostObject> getPostObjectsForSending(int number) {
		if (this.repository.size() < number) {
			number = this.repository.size();
		}
		
		List<PostObject> result = new ArrayList<PostObject>();
		
		synchronized (this.repository) {
			result.addAll(this.repository.subList(0, number));
			this.repository.removeAll(result);
			this.repository.notifyAll();
		}
		
		return result;
	}

	@Override
	public void run() {
		while (true) {
			while (this.getRepositorySize() <= POST_OBJECTS_THRESHOLD) {
				synchronized (this.repository) {
					try {
						this.repository.wait();
					} catch (InterruptedException e) {
						System.out.println("Post office stopped working.");
						return;
					}
				}
			}
			
			int size = this.getRepositorySize();
			int shipmentsPerPerson = size / this.postmen.size();
			
			for (Postman postman : this.postmen) {
				List<PostObject> postObjects = this.getPostObjectsForSending(shipmentsPerPerson);
				postman.setShipmentsToSend(postObjects);
			}
		}
	}

	@Override
	public Iterator<PostBox> iterator() {
		return new Iterator<PostBox>() {
			private int current = -1;

			@Override
			public boolean hasNext() {
				return (this.current + 1) < postBoxes.size();
			}

			@Override
			public PostBox next() {
				this.current++;
				return postBoxes.get(current);
			}
		};
	}
	
	public List<PostObject> getAllShipmentsByDate(Date date) {
		date = this.getOnlyDate(date);
		List<PostObject> result = new ArrayList<PostObject>();
		List<PostObject> shipmentsByDate = this.archive.get(date);
		result.addAll(shipmentsByDate);
		
		return result;
	}
	
	public float getLettersPercentForDay() {
		Date today = this.getOnlyDate(new Date());
		List<PostObject> postObjects = this.archive.get(today);
		int count = 0;
		
		for (PostObject postObject : postObjects) {
			if (postObject instanceof Letter) {
				count++;
			}
		}
		
		return ((float) count / (float) postObjects.size()) * PERCENT;
	}
	
	public float getBreakablePercent() {
		int breakableCount = 0;
		int packagesCount = 0;
		
		for (Date date : this.archive.keySet()) {
			List<PostObject> postObjects = this.archive.get(date);
			
			for (PostObject postObject : postObjects) {
				if (postObject instanceof Package) {
					packagesCount++;
					Package pack = (Package) postObject;
					
					if (pack.isBreakable()) {
						breakableCount++;
					}
				}
			}
		}
		
		return ((float) breakableCount / (float) packagesCount) * PERCENT;
	}
	
	public void showPostmenWorkDone() {
		Map<Integer, List<Postman>> postmenWork = new TreeMap<Integer, List<Postman>>((c1, c2) -> c2 - c1);
		
		for (Postman postman : this.postmen) {
			int count = postman.getShipmentsSentOutCount();
			
			if (count > 0) {
				if (!postmenWork.containsKey(count)) {
					List<Postman> list = new ArrayList<Postman>();
					postmenWork.put(count, list);
				}
				
				List<Postman> list = postmenWork.get(count);
				list.add(postman);
			}
		}
		
		for (Integer count : postmenWork.keySet()) {			
			System.out.print("Count sent out: " + count + " - ");
			List<Postman> list = postmenWork.get(count);
			
			for (Postman postman : list) {
				System.out.print(postman.getFirstName() + ", ");
			}
			
			System.out.println();
		}
	}
}
