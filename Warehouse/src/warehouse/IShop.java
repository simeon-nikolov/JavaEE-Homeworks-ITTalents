package warehouse;

public interface IShop extends Runnable {

	public abstract void buyProduct(String productName, int quantity);

	public abstract String getShopName();

	public abstract void stop();

	public abstract void run();

}