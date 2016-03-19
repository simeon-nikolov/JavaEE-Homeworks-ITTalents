package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import exceptions.WarehouseInvalidArgumentException;

public class Shop implements Runnable, IShop {
	private static final String PRODUCT_ADD_ERROR_MESSAGE = "Something went wrong when adding products to the shop.";
	private static final String SHOP_NAME_ERROR_MESSAGE = "The shop name is null or empty!";
	private static final String WAREHOUSE_ERROR_MESSAGE = "Warehouse is null!";
	private static final int TIME_BETWEEN_SUPPLY_CHECK = 2000;
	private static final int MIN_PRODUCTS_THRESHOLD = 4;
	
	private static AtomicInteger shopsFinishedWorking = new AtomicInteger(0);

	private Map<String, Product> products;
	private IWarehouse warehouse;
	private String shopName;
	private AtomicBoolean toStop;
	
	public Shop(IWarehouse warehouse, String shopName) throws WarehouseInvalidArgumentException {
		if (warehouse == null) {
			throw new WarehouseInvalidArgumentException(WAREHOUSE_ERROR_MESSAGE);
		}
		
		if (shopName == null || shopName.equals("")) {
			throw new WarehouseInvalidArgumentException(SHOP_NAME_ERROR_MESSAGE);
		}
		
		this.warehouse = warehouse;
		this.shopName = shopName;
		initializeProducts();
		this.toStop = new AtomicBoolean(false);
		
		for (String productName : this.products.keySet()) {
			this.supplyProduct(productName);
		}
	}
	
	private void initializeProducts() {
		this.products = new ConcurrentHashMap<String, Product>();
		
		try {
			List<Fruit> fruits = new ArrayList<Fruit>();
			fruits.add(new Fruit("Banana", 0));
			fruits.add(new Fruit("Orange", 0));
			fruits.add(new Fruit("Apple", 0));
			List<Vegetable> vegs = new ArrayList<Vegetable>();
			vegs.add(new Vegetable("Potato", 0));
			vegs.add(new Vegetable("Eggplant", 0));
			vegs.add(new Vegetable("Cucumber", 0));
			List<Meat> meats = new ArrayList<Meat>();
			meats.add(new Meat("Pork", 0));
			meats.add(new Meat("Beef", 0));
			meats.add(new Meat("Chicken", 0));

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

	private void supplyProduct(String productName) {
		Product product = this.products.get(productName);
		
		synchronized (product) {
			int quantity = 0;
			
			try {
				quantity = this.warehouse.takeStock(productName);
				product.setQuantity(product.getQuantity() + quantity);
			} catch (WarehouseInvalidArgumentException e) {
				e.printStackTrace();
			}
			
			this.products.put(productName, product);
			System.out.println(this.shopName + " supplied " + quantity + " " + 
					productName + " from the warehouse. Now it has: " + product.getQuantity());
		}
	}
	
	@Override
	public void buyProduct(String productName, int quantity) {
		IProduct product = this.products.get(productName);
		
		synchronized (product) {
			while (product.getQuantity() < MIN_PRODUCTS_THRESHOLD) {
				System.out.println(Thread.currentThread().getName() + " is waiting for " +
						this.shopName + " to supply " + productName);
				
				try {
					product.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try {
				product.setQuantity(product.getQuantity() - quantity);
			} catch (WarehouseInvalidArgumentException e) {
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName() + " bought " +
					quantity + " " + productName + " from " + this.shopName + 
					". Now there are: " + product.getQuantity());
		}
	}
	
	@Override
	public String getShopName() {
		return this.shopName;
	}
	
	public static AtomicInteger getShopsFinishedWorking() {
		return shopsFinishedWorking;
	}
	
	@Override
	public void stop() {
		this.toStop.set(true);
	}

	@Override
	public void run() {
		while (!this.toStop.get()) {
			try {
				Thread.sleep(TIME_BETWEEN_SUPPLY_CHECK);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			for (String productName : this.products.keySet()) {
				IProduct product = this.products.get(productName);
				
				synchronized (product) {
					if (product.getQuantity() < MIN_PRODUCTS_THRESHOLD) {
						System.out.println(this.shopName + " will try to supply " + productName);
						this.supplyProduct(productName);
					}
					
					product.notifyAll();
				}
			}
		}
		
		System.out.println(this.shopName + " stopped working because there are no more clients.");
		Shop.shopsFinishedWorking.incrementAndGet();
	}
}
