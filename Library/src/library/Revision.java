package library;

public class Revision implements Runnable {
	private static final int ONE_MONTH = 31000;
	
	private Library library;
	
	public Revision(Library library) {
		this.library = library;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(ONE_MONTH);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			
			System.out.println("Avaible readings in the library: " + this.library.getAvaibleCount());
		}
	}
	
}
