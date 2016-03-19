package warehouse;

import exceptions.WarehouseInvalidArgumentException;

public class Fruit extends Product {

	public Fruit(String name, int quantity)
			throws WarehouseInvalidArgumentException {
		super(name, quantity);
	}

}
