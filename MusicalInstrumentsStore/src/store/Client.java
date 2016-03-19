package store;

import exceptions.InvalidValueException;

public class Client implements Runnable {
	private static final String STORE_ERROR_MESSAGE = "Store is null reference!";
	private static final String NAME_ERROR_MESSAGE = "Name is null or empty string!";
	private static final int MIN_INSTRUMENTS_TO_BUY = 1;
	private static final int MAX_INSTRUMENTS_TO_BUY = 10;
	private static final int TIME_BETWEEN_BUYS = 1000;
	
	private String name;
	private Store store;
	
	public Client(String name, Store store) throws InvalidValueException {
		if (name == null || name.equals("")) {
			throw new InvalidValueException(NAME_ERROR_MESSAGE);
		}
		
		if (store == null) {
			throw new InvalidValueException(STORE_ERROR_MESSAGE);
		}
		
		this.name = name;
		this.store = store;
	}

	@Override
	public void run() {
		System.out.println(this.name + " started buying products.");
		
		while (true) {
			try {
				Thread.sleep(TIME_BETWEEN_BUYS);
			} catch (InterruptedException e) {
				System.out.println(this.name + " stopped buying products.");
				return;
			}
			
			String instrumentToBuy = Store.INSTRUMENT_NAMES[(int)(Math.random() * Store.INSTRUMENT_NAMES.length)];
			int quantityToBuy = (int) (Math.random() * (MAX_INSTRUMENTS_TO_BUY - MIN_INSTRUMENTS_TO_BUY) + MIN_INSTRUMENTS_TO_BUY);
			this.buyInstrument(instrumentToBuy, quantityToBuy);
		}
	}

	private void buyInstrument(String instrumentToBuy, int quantityToBuy) {
		try {
			System.out.println(this.name + " is buying " + quantityToBuy + " " + instrumentToBuy);
			this.store.sellInstrument(instrumentToBuy, quantityToBuy);
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
	}

}
