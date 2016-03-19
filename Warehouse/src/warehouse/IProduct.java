package warehouse;

import exceptions.WarehouseInvalidArgumentException;

public interface IProduct {

	public abstract String getName();

	public abstract void setName(String name)
			throws WarehouseInvalidArgumentException;

	public abstract int getQuantity();

	public abstract void setQuantity(int quantity)
			throws WarehouseInvalidArgumentException;

}