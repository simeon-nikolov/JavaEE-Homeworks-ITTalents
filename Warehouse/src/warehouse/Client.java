package warehouse;

import java.util.concurrent.atomic.AtomicInteger;

import exceptions.WarehouseInvalidArgumentException;

public class Client implements Runnable, IClient {
	private static final String SHOP_ERROR_MESSAGE = "Shop is null!";
	private static final String NAME_ERROR_MESSAGE = "Name of the client is null or empty string!";
	private static final int MIN_PRODUCTS_TO_BUY_AT_ONCE = 1;
	private static final int MAX_PRODUCTS_TO_BUY_AT_ONCE = 4;
	private static final int PRODUCTS_TO_BUY = 50;
	private static final String[] PRODUCT_NAMES;
	
	private static AtomicInteger finishedClientsCount;
	
	static {
		finishedClientsCount = new AtomicInteger(0);
		PRODUCT_NAMES = new String[] {
				"Banana", "Orange", "Apple", 
				"Potato", "Eggplant", "Cucumber",
				"Pork", "Beef", "Chicken",
			};
	}

	private String name;
	private IShop shop;
	
	public Client(String name, IShop shop) throws WarehouseInvalidArgumentException {
		if (name == null || name.equals("")) {
			throw new WarehouseInvalidArgumentException(NAME_ERROR_MESSAGE);
		}
		
		if (shop == null) {
			throw new WarehouseInvalidArgumentException(SHOP_ERROR_MESSAGE);
		}
		
		this.name = name;
		this.shop = shop;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public static AtomicInteger getFinishedClientsCount() {
		return finishedClientsCount;
	}

	@Override
	public void run() {
		System.out.println(this.name + " starts buying from " + this.shop.getShopName());
		int productsCount = 0;
		
		for (; productsCount < PRODUCTS_TO_BUY;) {
			int quantity = (int) (Math.random() * 
					(MAX_PRODUCTS_TO_BUY_AT_ONCE - MIN_PRODUCTS_TO_BUY_AT_ONCE) + 
					MIN_PRODUCTS_TO_BUY_AT_ONCE);
			int index = (int) (Math.random() * PRODUCT_NAMES.length);
			String product = PRODUCT_NAMES[index];
			this.shop.buyProduct(product, quantity);
			productsCount += quantity;
		}
		
		System.out.println(this.name + " bought " + productsCount + " products and now will stop buying.");
		Client.finishedClientsCount.incrementAndGet();
	}
}
