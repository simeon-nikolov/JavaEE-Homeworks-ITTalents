package warehouse;

public interface ISupplier extends Runnable {

	public abstract void run();

	public abstract void stop();

}