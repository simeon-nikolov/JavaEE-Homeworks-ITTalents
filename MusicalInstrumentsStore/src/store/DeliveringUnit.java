package store;

import exceptions.InvalidValueException;

public class DeliveringUnit implements Runnable {
	private static final String TIME_ERROR_MESSAGE = "Time is negative value!";
	private static final String QUANTITY_ERROR_MESSAGE = "Quantity must be positive value!";
	private static final String NAME_ERROR_MESSAGE = "Name is null or empty string!";
	private static final String STORE_ERROR_MESSAGE = "Store is null!";
	private Store storeToDeliverTo;
	private String productName;
	private int quantityToDeliver;
	private int timeToDeliver;
	
	public DeliveringUnit(Store store, String name, int quantity, int time) throws InvalidValueException {
		if (store == null) {
			throw new InvalidValueException(STORE_ERROR_MESSAGE);
		}
		
		if (name == null || name.equals("")) {
			throw new InvalidValueException(NAME_ERROR_MESSAGE);
		}
		
		if (quantity <= 0) {
			throw new InvalidValueException(QUANTITY_ERROR_MESSAGE);
		}
		
		if (time < 0) {
			throw new InvalidValueException(TIME_ERROR_MESSAGE);
		}
		
		this.storeToDeliverTo = store;
		this.productName = name;
		this.quantityToDeliver = quantity;
		this.timeToDeliver = time;
	}

	@Override
	public void run() {
		System.out.println("Delivering " + this.quantityToDeliver + " " + this.productName + " started.");
		
		try {
			Thread.sleep(this.timeToDeliver);
		} catch (InterruptedException e) {
			return;
		}
		
		try {
			this.storeToDeliverTo.addInstruments(this.productName, this.quantityToDeliver);
			System.out.println(this.quantityToDeliver + " " + this.productName + " was delivered!");
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
	}

}
