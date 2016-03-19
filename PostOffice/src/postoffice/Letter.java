package postoffice;

public class Letter extends PostObject {
	private static final int LETTER_TIME = 1000;
	private static final int LETTER_TAX = 50;

	public Letter(Citizen sender, Citizen receiver) {
		super(sender, receiver);
	}

	@Override
	public int getTax() {
		return LETTER_TAX;
	}

	@Override
	public int getTimeToSend() {
		return LETTER_TIME;
	}

	@Override
	public String toString() {
		return "Letter " + super.toString();
	}
}
