package postoffice;

public class Citizen extends Person implements Runnable {
	private static final int TIME_BETWEEN_SENDING = 1500;
	private String address;

	public Citizen(String firstName, String lastName, String address, PostOffice postOffice) {
		super(firstName, lastName, postOffice);
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}

	@Override
	public void run() {
		PostOffice postOffice = super.getPostOffice();
		
		while (true) {
			try {
				Thread.sleep(TIME_BETWEEN_SENDING);
			} catch (InterruptedException e) {
				System.out.println(super.getFirstName() + " will now stop sending.");
				return;
			}
			
			Citizen receiver = new Citizen("Random", "Person", "Somewhere on planet Earth.", postOffice);
			
			if (Math.random() < 0.5) {
				PostBox postBox = postOffice.getRandomPostBox();
				sendLetter(postBox, receiver);
				System.out.println(this.getFirstName() + " sent letter to post box " + postBox.getPostBoxNumber() + ".");
			} else {
				if (Math.random() < 0.5) {
					sendLetter(postOffice, receiver);
					System.out.println(this.getFirstName() + " sent letter to post office.");
				} else {
					sendPackage(postOffice, receiver);
					System.out.println(this.getFirstName() + " sent package to post office.");
				}
			}
		}
	}

	private void sendPackage(PostOffice postOffice, Citizen receiver) {
		int length = (int) (Math.random() * 100 + 20);
		int width = (int) (Math.random() * 100 + 20);
		int height = (int) (Math.random() * 100 + 20);
		boolean isBreakable = Math.random() < 0.5;
		Package packageToSend = new Package(this, receiver, length, width, height, isBreakable);
		postOffice.addPostObject(packageToSend);
	}

	private void sendLetter(LetterSendable post, Citizen receiver) {
		Letter letter = new Letter(this, receiver);
		post.sendLetter(letter);
	}

	@Override
	public String toString() {
		return super.toString() + " " + address;
	}
	
	
}
