package warehouse;

import java.util.concurrent.atomic.AtomicBoolean;

import exceptions.WarehouseInvalidArgumentException;

public class Supplier implements Runnable, ISupplier {
	private static final String WAREHOUSE_ERROR_MESSAGE = "Warehouse is null!";
	private static final int TIME_BETWEEN_SUPPLY_CHECK = 4000;

	private IWarehouse warehouse;
	private AtomicBoolean toStop;

	public Supplier(IWarehouse warehouse)
			throws WarehouseInvalidArgumentException {
		if (warehouse == null) {
			throw new WarehouseInvalidArgumentException(WAREHOUSE_ERROR_MESSAGE);
		}

		this.warehouse = warehouse;
		this.toStop = new AtomicBoolean(false);
	}

	@Override
	public void run() {
		while (!this.toStop.get()) {
			try {
				Thread.sleep(TIME_BETWEEN_SUPPLY_CHECK);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (this.warehouse) {
				while (!this.warehouse.needsSupplying()
						&& !this.toStop.get()) {
					try {
						this.warehouse.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (this.warehouse.needsSupplying()) {
					System.out.println("Supplier is delivering stock.");
					this.warehouse.deliverStock();
				}
			}
		}
		
		System.out.println("The supplier stopped supplying the warehouse.");
	}
	
	@Override
	public void stop() {
		this.toStop.set(true);
	}

}
