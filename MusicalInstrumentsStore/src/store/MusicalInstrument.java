package store;

import exceptions.InvalidValueException;

public class MusicalInstrument {
	private static final String PRICE_ERROR_MESSAGE = "Price can't be negative value!";
	private static final String NAME_ERROR_MESSAGE = "Name is null or empty!";
	
	private String name;
	private int price;
	
	public MusicalInstrument(String name) throws InvalidValueException {
		this.setName(name);
	}
	
	public MusicalInstrument(String name, int price) throws InvalidValueException {
		this(name);
		this.setPrice(price);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) throws InvalidValueException {
		if (name == null || name.equals("")) {
			throw new InvalidValueException(NAME_ERROR_MESSAGE);
		}
		
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) throws InvalidValueException {
		if (price < 0) {
			throw new InvalidValueException(PRICE_ERROR_MESSAGE);
		}
		
		this.price = price;
	}

	@Override
	public String toString() {
		return name + ", price: " + price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicalInstrument other = (MusicalInstrument) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
