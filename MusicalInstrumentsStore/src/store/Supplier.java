package store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import exceptions.InvalidValueException;

public class Supplier {
	private static final String NO_SUCH_INSTRUMENT_ERROR_MESSAGE = "There is no such instrument!";
	private static final String QAUNTITY_ERROR_MESSAGE = "Qauntity is negative value!";
	private static final String NAME_ERROR_MESSAGE = "Name is null or empty string!";
	private static final String STORE_ERROR_MESSAGE = "Store is null!";
	private static final int MIN_TIME_TO_WAIT = 3000;
	private static final int MAX_TIME_TO_WAIT = 5000;
	
	private Map<String, Integer> musicalInstrumentsToSupply; // name : time_to_deliver
	private Store store;
	
	public Supplier(Store store) throws InvalidValueException {
		if (store == null) {
			throw new InvalidValueException(STORE_ERROR_MESSAGE);
		}
		
		this.store = store;
		this.musicalInstrumentsToSupply = new ConcurrentHashMap<String, Integer>();
		
		for (String name : Store.INSTRUMENT_NAMES) {
			int time = (int) (Math.random() * (MAX_TIME_TO_WAIT - MIN_TIME_TO_WAIT) + MIN_TIME_TO_WAIT);
			this.musicalInstrumentsToSupply.put(name, time);
		}
	}
	
	public void takeInstruments(String name, int quantity) throws InvalidValueException {
		if (name == null || name.equals("")) {
			throw new InvalidValueException(NAME_ERROR_MESSAGE);
		}
		
		if (quantity <= 0) {
			throw new InvalidValueException(QAUNTITY_ERROR_MESSAGE);
		}
		
		if (!this.musicalInstrumentsToSupply.containsKey(name)) {
			throw new InvalidValueException(NO_SUCH_INSTRUMENT_ERROR_MESSAGE);
		}
		
		int timeToWait = this.musicalInstrumentsToSupply.get(name);
		System.out.println("You'll have to wait " + timeToWait + " for your delivery for " + quantity + " " + name);
		new Thread(new DeliveringUnit(this.store, name, quantity, timeToWait)).start();
	}

}
