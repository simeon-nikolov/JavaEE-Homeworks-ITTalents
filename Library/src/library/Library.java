package library;

import java.util.Date;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import library.History.Rent;

public class Library implements Runnable {
	private static final double INTEREST_COEFFICIENT = 0.01;
	private static final int TIME_BETWEEN_READINGS_CHECKS = 1000;
	protected static final String BOOKS_TYPE_NAME = "Books";
	protected static final String SCHOOLBOOKS_TYPE_NAME = "Schoolbooks";
	protected static final String MAGAZINES_TYPE_NAME = "Magazines";
	protected static final String[] READINGS_TYPES = {MAGAZINES_TYPE_NAME, SCHOOLBOOKS_TYPE_NAME, BOOKS_TYPE_NAME};
	
	private Map<String, Map<Readable, Readable>> readings = new ConcurrentHashMap<String, Map<Readable, Readable>>();
	
	public Library() {
		for (String type : READINGS_TYPES) {
			Map<Readable, Readable> readingsByType = new ConcurrentHashMap<Readable, Readable>();
			this.readings.put(type, readingsByType);
		}
	}
	
	public void addReading(Readable reading, String type) {
		Map<Readable, Readable> readingsInType = this.readings.get(type);
		readingsInType.put(reading, reading);
	}
	
	public void listMagazines() {
		Map<Readable, Readable> magazines = this.readings.get(MAGAZINES_TYPE_NAME);
		Map<String, SortedSet<Magazine>> magazinesByCategory = 
				new TreeMap<String, SortedSet<Magazine>>((c1, c2) -> c1.compareTo(c2));
		
		for (Readable magazine : magazines.keySet()) {
			String category = ((Magazine) magazine).getCategory();
			
			if (!magazinesByCategory.containsKey(category)) {
				SortedSet<Magazine> magazinesInCategory = 
						new TreeSet<Magazine>((m1, m2) -> 
							{
								int result = 0;
								
								if (m1.getTitle().equals(m2.getTitle())) {
									result = m1.getIssueNumber() - m2.getIssueNumber();
									result = result == 0 ? 1 : result;
								} else {
									result = m1.getTitle().compareTo(m2.getTitle());
								}
								
								return result;
							}
						);
				
				magazinesByCategory.put(category, magazinesInCategory);
			}
			
			SortedSet<Magazine> magazinesInCategory = magazinesByCategory.get(category);
			magazinesInCategory.add((Magazine) magazine);
		}
		
		for (String category : magazinesByCategory.keySet()) {
			SortedSet<Magazine> magazinesInCategory = magazinesByCategory.get(category);
			System.out.println(category + ":");
			
			for (Magazine magazine : magazinesInCategory) {
				System.out.println(" \"" + magazine.getTitle() + "\" issue " + magazine.getIssueNumber());
			}
		}
	}
	
	public void listBooks() {
		Map<String, SortedSet<Book>> booksByGenres = 
				new TreeMap<String, SortedSet<Book>>((g1, g2) -> g1.compareTo(g2));
		Map<Readable, Readable> books = this.readings.get(BOOKS_TYPE_NAME);
		
		for (Readable readable : books.keySet()) {
			Book book = (Book) readable;
			String genre = book.getGenre();
			
			if (!booksByGenres.containsKey(genre)) {
				SortedSet<Book> booksInGenre = 
						new TreeSet<Book>((b1, b2) -> (b1.getDateOfPublishing().
								compareTo(b2.getDateOfPublishing())));
				booksByGenres.put(genre, booksInGenre);
			}
			
			SortedSet<Book> booksInGenre = booksByGenres.get(genre);
			booksInGenre.add(book);
		}
		
		for (String genre : booksByGenres.keySet()) {
			SortedSet<Book> booksInGenre = booksByGenres.get(genre);
			System.out.println(genre + ":");
			
			for (Book book : booksInGenre) {
				System.out.println(" \"" + book.getTitle() + "\" by " + book.getAuthor() + " - " + book.getDateOfPublishing());
			}
		}
	}
	
	public void listSchoolbooks() {
		Map<String, SortedSet<Schoolbook>> schoolbooksBySubjects = 
				new TreeMap<String, SortedSet<Schoolbook>>((g1, g2) -> g1.compareTo(g2));
		Map<Readable, Readable> schoolbooks = this.readings.get(SCHOOLBOOKS_TYPE_NAME);
		
		for (Readable readable : schoolbooks.keySet()) {
			Schoolbook schoolbook = (Schoolbook) readable;
			String subject = schoolbook.getSubject();
			
			if (!schoolbooksBySubjects.containsKey(subject)) {
				SortedSet<Schoolbook> schoolbooksInGenre = 
						new TreeSet<Schoolbook>((sb1, sb2) -> (sb1.getTitle().
								compareTo(sb2.getTitle())));
				schoolbooksBySubjects.put(subject, schoolbooksInGenre);
			}
			
			SortedSet<Schoolbook> booksInGenre = schoolbooksBySubjects.get(subject);
			booksInGenre.add(schoolbook);
		}
		
		for (String subject : schoolbooksBySubjects.keySet()) {
			SortedSet<Schoolbook> schoolbooksInGenre = schoolbooksBySubjects.get(subject);
			System.out.println(subject + ":");
			
			for (Schoolbook schoolbook : schoolbooksInGenre) {
				System.out.println(" \"" + schoolbook.getTitle() + "\"");
			}
		}
	}
	
	public void rentReading(Readable reading) {
		for (String type : this.readings.keySet()) {
			Map<Readable, Readable> readingsByType = this.readings.get(type);
			
			if (readingsByType.containsKey(reading)) {
				reading = readingsByType.get(reading);
				
				if (reading instanceof Magazine) {
					System.out.println("Magazines can't be rented!");
				} else {
					if (this.isAvaible(reading)) {
						History history = reading.getHistory();
						Rent rent = new History().new Rent(reading.getTax(), reading.getRentTime());
						history.addRentToHistory(rent);
						System.out.println(reading.getTitle() + " was rented for " + reading.getRentTime());
					}
				}
			}
		}
	}
	
	private boolean isAvaible(Readable reading) {
		boolean isAvaible = true;
		History history = reading.getHistory();
		
		if (history.getSize() > 0) {
			Rent rent = history.getLastRent();
			isAvaible = rent.getDateOfReturn() != null;
		}
		
		return isAvaible;
	}

	public void returnRentedReading(Readable reading) {
		for (String type : this.readings.keySet()) {
			Map<Readable, Readable> readingsByType = this.readings.get(type);
			
			if (readingsByType.containsKey(reading)) {
				reading = readingsByType.get(reading);
				History history = reading.getHistory();
				if (history.getSize() > 0) {
					Rent rent = history.getLastRent();
					
					if (rent.getDateOfReturn() == null) {
						rent.setDateOfReturn(new Date());
						System.out.println(reading.getTitle() + " was returned.");
						System.out.println("Tax to pay: " + rent.getTax() + 
								", Interest: " + rent.getInterest() + ". All: " + 
								(rent.getTax() + rent.getInterest()));
					}
				}
			}
		}
	}
	
	public int getAvaibleCount() {
		int count = 0;
		
		for (String type : this.readings.keySet()) {
			Map<Readable, Readable> readingsByType = this.readings.get(type);
			
			for (Readable reading : readingsByType.keySet()) {
				History history = reading.getHistory();
				
				if (history.getSize() > 0) {
					Rent rent = history.getLastRent();
					
					if (rent.getDateOfReturn() != null) {
						count++;
					}
				} else {
					count++;
				}
			}
		}
		
		return count;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(TIME_BETWEEN_READINGS_CHECKS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			
			for (String type : this.readings.keySet()) {
				Map<Readable, Readable> readingsByType = this.readings.get(type);
				
				for (Readable reading : readingsByType.keySet()) {
					History history = reading.getHistory();
					
					if (history.getSize() > 0) {
						Rent rent = history.getLastRent();
						
						if (rent.getDateOfReturn() == null) {
							Date date = new Date();
							long differenceTime = date.getTime() - rent.getDueDate().getTime();
							
							if (differenceTime > 0) {
								int interest = (int) (rent.getTax() * INTEREST_COEFFICIENT);
								rent.setInterest(rent.getInterest() + interest);
								System.out.println(reading.getTitle() + " was charged with " + interest + ". "
										+ "Current interest: " + rent.getInterest());
							}
						}
					}
				}
			}
		}
	}
}
