package store;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import exceptions.InvalidValueException;

public class Store implements Runnable {
	private static final int PRODUCTS_TO_ORDER_FOR_SUPPLY = 10;
	private static final int SUPPLY_TIME = 10000;
	private static final int MIN_INSTRUMENT_PRICE = 4500; // in stotinkas = 45 lv
	private static final int MAX_INSTRUMENT_PRICE = 90000; // in stotinkas = 900 lv
	private static final int STRINGS_COUNT = 6;
	private static final int DRUMS_COUNT = 5;
	private static final int KEYBOARD_COUNT = 3;
	private static final int ELECTRONIC_COUNT = 3;
	private static final int STRINGS_TYPE_INDEX = 0;
	private static final int DRUM_TYPE_INDEX = 1;
	private static final int WIND_TYPE_INDEX = 2;
	private static final int KEYBOARD_TYPE_INDEX = 3;
	private static final int ELECTRONIC_TYPE_INDEX = 4;
	static final String[] INSTRUMENT_NAMES = { 
		"violin", "viola", "contrabass", "harp", "guitar", "fiddle", // strings
		"drums", "tarambuka", "drum", // drums
		"tambourine", "trumpet", "trombone", "tuba", "flute", "clarinet", // wind
		"pipe organ", "piano", "accordion", // keyboard
		"synthesizer", "bass guitar", "electronic violin", // electronic
	};
	static final String[] TYPES_OF_MUSICAL_INSTRUMENT = { "strings", "drums", "wind", "keyboard", "electronic", };

	private Map<String, Map<MusicalInstrument, Integer>> musicalInstruments; // type : [instrument : quantity]
	private Map<String, Integer> soldInstruments; // name : quantity sold
	private AtomicInteger money; // in stotinkas
	private Supplier supplier;

	public Store() {
		this.musicalInstruments = new ConcurrentHashMap<String, Map<MusicalInstrument, Integer>>();
		this.soldInstruments = new ConcurrentHashMap<String, Integer>();
		this.money = new AtomicInteger(0);
		
		try {
			this.supplier = new Supplier(this);
		} catch (InvalidValueException e1) {
			System.err.println(e1.getMessage());
		}

		for (int index = 0; index < INSTRUMENT_NAMES.length; index++) {
			int typeIndex = getTypeIndexByInstrumentIndex(index);
			String name = INSTRUMENT_NAMES[index];
			String type = TYPES_OF_MUSICAL_INSTRUMENT[typeIndex];
			int price = (int) (Math.random()
					* ((MAX_INSTRUMENT_PRICE + 1) - MIN_INSTRUMENT_PRICE) + MIN_INSTRUMENT_PRICE);
			MusicalInstrument instrument = null;

			try {
				instrument = new MusicalInstrument(name, price);
			} catch (InvalidValueException e) {
				e.printStackTrace();
			}

			if (!this.musicalInstruments.containsKey(type)) {
				Map<MusicalInstrument, Integer> instrumentsByType = new ConcurrentHashMap<MusicalInstrument, Integer>();
				this.musicalInstruments.put(type, instrumentsByType);
			}

			Map<MusicalInstrument, Integer> instruments = this.musicalInstruments.get(type);
			instruments.put(instrument, 0);
		}
	}

	private int getTypeIndexByInstrumentIndex(int index) {
		int typeIndex = ELECTRONIC_TYPE_INDEX;

		if (index < INSTRUMENT_NAMES.length - ELECTRONIC_COUNT - 1) {
			typeIndex = KEYBOARD_TYPE_INDEX;
		}

		if (index < INSTRUMENT_NAMES.length - ELECTRONIC_COUNT - KEYBOARD_COUNT
				- 1) {
			typeIndex = WIND_TYPE_INDEX;
		}

		if (index < STRINGS_COUNT + DRUMS_COUNT) {
			typeIndex = DRUM_TYPE_INDEX;
		}

		if (index < STRINGS_COUNT) {
			typeIndex = STRINGS_TYPE_INDEX;
		}

		return typeIndex;
	}

	public void sellInstrument(String name, int quantity)
			throws InvalidValueException {
		this.validateNameAndQantity(name, quantity);
		String type = this.getTypeByName(name);
		MusicalInstrument instrument = this.findInstrument(name);

		if (instrument != null) {
			Map<MusicalInstrument, Integer> instrumentsByType = this.musicalInstruments.get(type);
			int instrumentQuantity = instrumentsByType.get(instrument);

			if (instrumentQuantity > quantity) {
				instrumentsByType.put(instrument, (instrumentQuantity - quantity));
				int allSold = quantity;

				if (this.soldInstruments.containsKey(name)) {
					allSold += this.soldInstruments.get(name);
				}

				this.soldInstruments.put(name, allSold);
				this.money.addAndGet(instrument.getPrice());
				System.out.println(quantity + " " + name + " were sold.");
			} else {
				System.out.println("There is not enough quantity in the store right now.");
				System.out.println("Requesting a supply for " + quantity + " " + name);
				this.supplier.takeInstruments(name, quantity);
			}
		}
	}

	public void addInstruments(String name, int quantity)
			throws InvalidValueException {
		this.validateNameAndQantity(name, quantity);
		MusicalInstrument instrument = this.findInstrument(name);
		String type = this.getTypeByName(name);
		
		if (instrument != null) {
			Map<MusicalInstrument, Integer> instrumentsByType = this.musicalInstruments.get(type);
			int oldQuantity = instrumentsByType.get(instrument);
			instrumentsByType.put(instrument, oldQuantity + quantity);
			System.out.println(quantity + " " + name + " were added in the store.");
		}
	}

	private MusicalInstrument findInstrument(String name) {
		MusicalInstrument instrument = null;

		for (String type : this.musicalInstruments.keySet()) {
			Map<MusicalInstrument, Integer> instrumentsByType = this.musicalInstruments.get(type);
			
			try {
				MusicalInstrument instrumentToFind = new MusicalInstrument(name);
				
				if (instrumentsByType.containsKey(instrumentToFind)) {
					for (MusicalInstrument currentInstrument : instrumentsByType.keySet()) {
						if (instrumentToFind.equals(currentInstrument)) {
							instrument = currentInstrument;
							break;
						}
					}
					
					break;
				}
			} catch (InvalidValueException e) {
				e.printStackTrace();
			}
		}

		if (instrument == null) {
			System.out.println("There is no such instrument: " + name);
		}

		return instrument;
	}

	private void validateNameAndQantity(String name, int quantity)
			throws InvalidValueException {
		if (name == null || name.equals("")) {
			throw new InvalidValueException("Name is null or empty!");
		}

		if (quantity < 0) {
			throw new InvalidValueException("Quantity can't be negative value!");
		}
	}

	public void listIntrumentsByType() {
		for (String type : this.musicalInstruments.keySet()) {
			Map<MusicalInstrument, Integer> instrumentsByType = this.musicalInstruments.get(type);
			System.out.println(type + ":");

			for (MusicalInstrument instrument : instrumentsByType.keySet()) {
				System.out.println(" - " + instrument);
			}
		}
	}

	public void listInstrumentsByName() {
		SortedSet<MusicalInstrument> instrumentsByName = new TreeSet<MusicalInstrument>(
				(m1, m2) -> m1.getName().compareTo(m2.getName()));

		list(instrumentsByName);
	}

	public void listInstrumentsByPrice(boolean isAscending) {
		SortedSet<MusicalInstrument> instrumentsByPrice = new TreeSet<MusicalInstrument>(
				(m1, m2) -> (isAscending ? m1.getPrice() - m2.getPrice() : m2
						.getPrice() - m1.getPrice()));

		list(instrumentsByPrice);
	}

	public void listAvaibleInstruments() {
		for (String type : this.musicalInstruments.keySet()) {
			Map<MusicalInstrument, Integer> instrumentsByType = this.musicalInstruments.get(type);

			for (MusicalInstrument instrument : instrumentsByType.keySet()) {
				if (instrumentsByType.get(instrument) > 0) {
					System.out.println(instrument);
				}
			}
		}
	}

	private void list(SortedSet<MusicalInstrument> instruments) {
		for (String type : this.musicalInstruments.keySet()) {
			Map<MusicalInstrument, Integer> instrumentByType = this.musicalInstruments.get(type);

			for (MusicalInstrument instrument : instrumentByType.keySet()) {
				instruments.add(instrument);
			}
		}

		for (MusicalInstrument musicalInstrument : instruments) {
			System.out.println(musicalInstrument);
		}
	}

	public void listSoldInstrumentsByQuantity() {
		SortedMap<Integer, Set<String>> instrumentsByQuantity = new TreeMap<Integer, Set<String>>((e1, e2) -> e1 - e2);
		this.getSoldInstrumentsByQuantity(instrumentsByQuantity);
		
		for (Integer quantity : instrumentsByQuantity.keySet()) {
			Set<String> names = instrumentsByQuantity.get(quantity);
			System.out.println(quantity + " - ");

			for (String name : names) {
				System.out.print(name + " ");
			}
		}
	}

	public int getSumOfAllSales() {
		int sum = 0;

		for (String name : this.soldInstruments.keySet()) {
			MusicalInstrument instrument = this.findInstrument(name);

			if (instrument != null) {
				sum += instrument.getPrice();
			}
		}

		return sum;
	}

	public void listMostSoldInstruments() {
		SortedMap<Integer, Set<String>> instrumentsByQuantity = new TreeMap<Integer, Set<String>>((e1, e2) -> e2 - e1);
		this.getSoldInstrumentsByQuantity(instrumentsByQuantity);
		
		if (instrumentsByQuantity.size() > 0) {
			int maxQuantity = instrumentsByQuantity.firstKey();
			Set<String> namesOfMostSoldIntruments = instrumentsByQuantity.get(maxQuantity);
			System.out.println("Most sold instruments by quantity: ");

			for (String name : namesOfMostSoldIntruments) {
				System.out.println(name + " (quantity sold : " + maxQuantity);
			}
		}
	}

	public void listLeastSoldInstruments() {
		SortedMap<Integer, Set<String>> instrumentsByQuantity = new TreeMap<Integer, Set<String>>((e1, e2) -> e1 - e2);
		this.getSoldInstrumentsByQuantity(instrumentsByQuantity);
		
		if (instrumentsByQuantity.size() > 0) {
			int leastQuantity = instrumentsByQuantity.firstKey();
			Set<String> namesOfLeastSoldIntruments = instrumentsByQuantity.get(leastQuantity);
			System.out.println("Least sold instruments by quantity: ");

			for (String name : namesOfLeastSoldIntruments) {
				System.out.println(name + " (quantity sold : " + leastQuantity);
			}
		}
	}

	private void getSoldInstrumentsByQuantity(SortedMap<Integer, Set<String>> instrumentsByQuantity) {
		for (String name : this.soldInstruments.keySet()) {
			int quantity = this.soldInstruments.get(name);

			if (!instrumentsByQuantity.containsKey(quantity)) {
				Set<String> names = new HashSet<String>();
				instrumentsByQuantity.put(quantity, names);
			}

			Set<String> namesByQuantity = instrumentsByQuantity.get(quantity);
			namesByQuantity.add(name);
		}
	}

	public void listMostSoldTypes() {
		Map<String, Integer> typesSold = new HashMap<String, Integer>();

		// count sold quantity of types
		for (String name : this.soldInstruments.keySet()) {
			String type = this.getTypeByName(name);

			if (!typesSold.containsKey(type)) {
				typesSold.put(type, 0);
			}

			int quantity = typesSold.get(type);
			quantity += this.soldInstruments.get(name);
			typesSold.put(type, quantity);
		}

		// kinda sorting....
		SortedMap<Integer, Set<String>> quantityOfTypes = new TreeMap<Integer, Set<String>>((q1, q2) -> q2 - q1);
		this.addToSortedMap(typesSold, quantityOfTypes);

		if (quantityOfTypes.size() > 0) {
			// find the max
			int maxQuantity = quantityOfTypes.firstKey();
			Set<String> typesByMaxQuantity = quantityOfTypes.get(maxQuantity);
			System.out.println("Most sold types (quantity : " + maxQuantity + "): ");

			// print 'em
			for (String typeName : typesByMaxQuantity) {
				System.out.println(typeName);
			}
		}
	}

	public void listMostProfitableTypes() {
		Map<String, Integer> typesSold = new HashMap<String, Integer>();

		// count the sum of sold instruments by type
		for (String name : this.soldInstruments.keySet()) {
			String type = this.getTypeByName(name);

			if (!typesSold.containsKey(type)) {
				typesSold.put(type, 0);
			}

			int sum = typesSold.get(type);
			MusicalInstrument instrument = this.findInstrument(name);

			if (instrument != null) {
				sum += instrument.getPrice();
			}

			typesSold.put(type, sum);
		}

		// kinda sorting....
		SortedMap<Integer, Set<String>> sumOfTypes = new TreeMap<Integer, Set<String>>((q1, q2) -> q2 - q1);
		this.addToSortedMap(typesSold, sumOfTypes);

		if (sumOfTypes.size() > 0) {
			// find the max
			int maxSum = sumOfTypes.firstKey();
			Set<String> typesByMostProfit = sumOfTypes.get(maxSum);
			System.out.println("Most profitable types (profit : " + maxSum + "): ");

			// print 'em
			for (String typeName : typesByMostProfit) {
				System.out.println(typeName);
			}
		}
	}

	private void addToSortedMap(Map<String, Integer> typesSold, SortedMap<Integer, Set<String>> values) {
		for (String type : typesSold.keySet()) {
			int value = typesSold.get(type);

			if (!values.containsKey(value)) {
				Set<String> types = new HashSet<String>();
				values.put(value, types);
			}

			Set<String> typesByValues = values.get(value);
			typesByValues.add(type);
		}
	}

	private String getTypeByName(String name) {
		String typeOfInstrument = null;

		for (String type : this.musicalInstruments.keySet()) {
			Map<MusicalInstrument, Integer> instruments = this.musicalInstruments.get(type);

			try {
				MusicalInstrument instrument = new MusicalInstrument(name, 0);

				if (instruments.containsKey(instrument)) {
					typeOfInstrument = type;
					break;
				}
			} catch (InvalidValueException e) {
				e.printStackTrace();
			}
		}

		return typeOfInstrument;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(SUPPLY_TIME);
			} catch (InterruptedException e) {
				return;
			}
			
			System.out.println("Checking for monthly supplying.");
			
			for (String type : this.musicalInstruments.keySet()) {
				Map<MusicalInstrument, Integer> instrumentsByType = this.musicalInstruments.get(type);
				
				for (MusicalInstrument instrument : instrumentsByType.keySet()) {
					int quantity = instrumentsByType.get(instrument);
					
					if (quantity == 0) {
						System.out.println("Requesting a supply for " + instrument.getName());
						
						try {
							this.supplier.takeInstruments(instrument.getName(), PRODUCTS_TO_ORDER_FOR_SUPPLY);
						} catch (InvalidValueException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
