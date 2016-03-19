package postoffice;

import java.util.List;

public class JuniorPostman extends Postman implements Runnable {
	private static final int TIME_BETWEEN_COLLECTING_FROM_POST_BOXES = 100;
	private static final int TIME_TO_GET_ALL_LETTERS = 10000;

	public JuniorPostman(String firstName, String lastName, int yearsWork, PostOffice postOffice) {
		super(firstName, lastName, yearsWork, postOffice);
	}

	@Override
	public void run() {
		PostOffice postOffice = super.getPostOffice();
		
		while (true) {
			while (postOffice.getRepositorySize() > PostOffice.POST_OBJECTS_THRESHOLD) {
				synchronized (postOffice) {
					try {
						postOffice.wait();
					} catch (InterruptedException e) {
						System.out.println(super.getFirstName() + " will now stop working.");
						return;
					}
				}
			}
			
			for (PostBox postBox : postOffice) {
				List<Letter> letters = postBox.getAllLetters();
				postOffice.addLetters(letters);
				System.out.println("Junior postman " + super.getFirstName()  + 
						" collected " + letters.size() + 
						" letters from post box number: " + 
						postBox.getPostBoxNumber());
				
				try {
					Thread.sleep(TIME_BETWEEN_COLLECTING_FROM_POST_BOXES);
				} catch (InterruptedException e) {
					System.out.println(super.getFirstName() + " will now stop working.");
					return;
				}
			}
			
			try {
				Thread.sleep(TIME_TO_GET_ALL_LETTERS);
			} catch (InterruptedException e) {
				System.out.println(super.getFirstName() + " will now stop working.");
				return;
			}
		}
	}
	
	@Override
	public int getShipmentsSentOutCount() {
		return 0;
	}
}
