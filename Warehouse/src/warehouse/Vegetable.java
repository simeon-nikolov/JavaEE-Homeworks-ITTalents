package warehouse;

import exceptions.WarehouseInvalidArgumentException;

public class Vegetable extends Product {

	public Vegetable(String name, int quantity)
			throws WarehouseInvalidArgumentException {
		super(name, quantity);
	}

}
