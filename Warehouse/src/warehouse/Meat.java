package warehouse;

import exceptions.WarehouseInvalidArgumentException;

public class Meat extends Product {

	public Meat(String name, int quantity)
			throws WarehouseInvalidArgumentException {
		super(name, quantity);
	}


}
