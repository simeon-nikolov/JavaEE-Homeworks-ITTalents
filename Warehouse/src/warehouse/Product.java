package warehouse;

import exceptions.WarehouseInvalidArgumentException;

public abstract class Product implements IProduct {
	private static final String QUANTITY_ERROR_MESSAGE = "Quantity can't be a negative value!";
	private static final String PROUCT_NAME_ERROR_MESSAGE = "The name of the product is null or empty string.";
	
	private String name;
	private int quantity;
	
	public Product(String name, int quantity) throws WarehouseInvalidArgumentException {
		this.setName(name);
		this.setQuantity(quantity);
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) throws WarehouseInvalidArgumentException {
		if (name == null || name.equals("")) {
			throw new WarehouseInvalidArgumentException(PROUCT_NAME_ERROR_MESSAGE);
		}
		
		this.name = name;
	}
	
	@Override
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public void setQuantity(int quantity) throws WarehouseInvalidArgumentException {
		if (quantity < 0) {
			throw new WarehouseInvalidArgumentException(QUANTITY_ERROR_MESSAGE);
		}

		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + quantity;
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
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}
}
