package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import exceptions.WarehouseInvalidArgumentException;

public class Warehouse implements IWarehouse {
	private static final String NO_SUCH_PRODUCT_ERROR_MESSAGE = "There is no such product!";
	private static final String PRODUCT_NAME_ERROR_MESSAGE = "Product name is null or empty string.";
	private static final String PRODUCT_ADD_ERROR_MESSAGE = "Something went wrong when adding products in the warehouse.";
	private static final int QUANTITY_TO_BUY = 5;
	private static final int PRODUCT_SUPPLY_COUNT = 25;
	private static final int INITIAL_COUNT_OF_PRODUCTS = 15;
	private static final int DEFICIT_STOCK_COUNT = 5;

	private Map<String, IProduct> products;
	private volatile boolean needsSupplying; // <- is volatile even doing anything???

	public Warehouse() {
		this.products = new ConcurrentHashMap<String, IProduct>();

		try {
			List<Fruit> fruits = new ArrayList<Fruit>();
			fruits.add(new Fruit("Banana", INITIAL_COUNT_OF_PRODUCTS));
			fruits.add(new Fruit("Orange", INITIAL_COUNT_OF_PRODUCTS));
			fruits.add(new Fruit("Apple", INITIAL_COUNT_OF_PRODUCTS));
			List<Vegetable> vegs = new ArrayList<Vegetable>();
			vegs.add(new Vegetable("Potato", INITIAL_COUNT_OF_PRODUCTS));
			vegs.add(new Vegetable("Eggplant", INITIAL_COUNT_OF_PRODUCTS));
			vegs.add(new Vegetable("Cucumber", INITIAL_COUNT_OF_PRODUCTS));
			List<Meat> meats = new ArrayList<Meat>();
			meats.add(new Meat("Pork", INITIAL_COUNT_OF_PRODUCTS));
			meats.add(new Meat("Beef", INITIAL_COUNT_OF_PRODUCTS));
			meats.add(new Meat("Chicken", INITIAL_COUNT_OF_PRODUCTS));

			for (Fruit fruit : fruits) {
				this.products.put(fruit.getName(), fruit);
			}

			for (Vegetable vegetable : vegs) {
				this.products.put(vegetable.getName(), vegetable);
			}

			for (Meat meat : meats) {
				this.products.put(meat.getName(), meat);
			}
		} catch (WarehouseInvalidArgumentException e) {
			System.err.println(PRODUCT_ADD_ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void deliverStock() {
		for (String productName : products.keySet()) {
			IProduct product = this.products.get(productName);
			
			synchronized (product) {
				if (product.getQuantity() <= DEFICIT_STOCK_COUNT) {
					try {
						product.setQuantity(product.getQuantity()
								+ PRODUCT_SUPPLY_COUNT);
						System.out.println("Warehouse was supplied with "
								+ PRODUCT_SUPPLY_COUNT + " " + product.getName() +
								". Now there are: " + product.getQuantity() + " "
								+ product.getName());
						product.notifyAll();
					} catch (WarehouseInvalidArgumentException e) {
						e.printStackTrace();
					}
				}	
			}
		}

		this.needsSupplying = false;
	}

	@Override
	public int takeStock(String productName)
			throws WarehouseInvalidArgumentException {
		if (productName == null || productName.equals("")) {
			throw new WarehouseInvalidArgumentException(PRODUCT_NAME_ERROR_MESSAGE);
		}

		IProduct product = this.products.get(productName);

		if (product != null) {
			synchronized (product) {
				while (product.getQuantity() < DEFICIT_STOCK_COUNT) {
					System.out.println(Thread.currentThread().getName() +
							" is waiting for the warehouse to be supplied with " + productName);
					
					try {
						product.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				product.setQuantity(product.getQuantity() - QUANTITY_TO_BUY);
				System.out.println(QUANTITY_TO_BUY + " " + productName
						+ " were sold from the warehouse to " + Thread.currentThread().getName() +
						". Now there are: " + product.getQuantity() + " in the warehouse.");
			}
			
			synchronized (this) {
				if (product.getQuantity() < DEFICIT_STOCK_COUNT) {
					this.needsSupplying = true;
					this.notifyAll();
				}
			}
		} else {
			throw new WarehouseInvalidArgumentException(NO_SUCH_PRODUCT_ERROR_MESSAGE);
		}
		
		return QUANTITY_TO_BUY;
	}

	@Override
	public boolean needsSupplying() {
		return this.needsSupplying;
	}
}
