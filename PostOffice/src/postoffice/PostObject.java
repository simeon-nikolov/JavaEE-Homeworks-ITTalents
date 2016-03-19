package postoffice;

public abstract class PostObject {
	private Citizen sender;
	private Citizen receiver;
	
	public PostObject(Citizen sender, Citizen receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public Citizen getSender() {
		return sender;
	}
	
	public Citizen getReceiver() {
		return receiver;
	}
	
	public abstract int getTax();
	
	public abstract int getTimeToSend();

	@Override
	public String toString() {
		return "From: " + sender + ", To: " + receiver;
	}
}
