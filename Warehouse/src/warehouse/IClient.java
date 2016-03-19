package warehouse;

public interface IClient extends Runnable {

	public abstract String getName();

	public abstract void run();

}