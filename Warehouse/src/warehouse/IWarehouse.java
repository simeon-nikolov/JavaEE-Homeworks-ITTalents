package warehouse;

import exceptions.WarehouseInvalidArgumentException;

public interface IWarehouse {

	public abstract void deliverStock();

	public abstract int takeStock(String productName)
			throws WarehouseInvalidArgumentException;

	public abstract boolean needsSupplying();

}