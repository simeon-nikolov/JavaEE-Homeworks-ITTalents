package postoffice;

import java.util.Date;
import java.util.List;

public class Demo {
	private static final int WAIT_TIME_FOR_THREADS_TO_STOP = 3000;
	private static final int TIME_BEFORE_TERMINATE = 45000;
	private static final int JUNIOR_POSTMEN = 3;
	private static final int POSTMEN_COUNT = 6;
	private static final int NUMBER_OF_CITIZENS = 10;

	public static void main(String[] args) {
		PostOffice postOffice = new PostOffice();
		Thread p = new Thread(postOffice);
		p.start();
		
		// Create citizens
		ThreadGroup citizens = new ThreadGroup("Citizens");
		
		for (int citizenIndex = 1; citizenIndex <= NUMBER_OF_CITIZENS; citizenIndex++) {
			Citizen citizen = new Citizen("citizen" + citizenIndex, "lastName" + citizenIndex, "address" + citizenIndex, postOffice);
			Thread t = new Thread(citizens, citizen);
			t.start();
		}
		
		// Create postmen
		ThreadGroup postmenThreadGroup = new ThreadGroup("Postmen");
		
		for (int postmanIndex = 1; postmanIndex <= POSTMEN_COUNT; postmanIndex++) {
			Postman postman = new Postman("postman" + postmanIndex, 
					"lastName" + postmanIndex, (int)(Math.random() * 15 + 5), postOffice);
			Thread t = new Thread(postmenThreadGroup, postman);
			t.start();
		}
		
		// Create junior postmen
		ThreadGroup juniorPostmenGroup = new ThreadGroup("Junior Postmen");
		
		for (int postmanIndex = 1; postmanIndex <= JUNIOR_POSTMEN; postmanIndex++) {
			JuniorPostman juniorPostman = new JuniorPostman("junior" + postmanIndex, 
					"lastName" + postmanIndex, (int)(Math.random() * 5), postOffice);
			Thread t = new Thread(juniorPostmenGroup, juniorPostman);
			t.start();
		}
		
		try {
			Thread.sleep(TIME_BEFORE_TERMINATE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		citizens.interrupt();
		postmenThreadGroup.interrupt();
		juniorPostmenGroup.interrupt();
		
		try {
			Thread.sleep(WAIT_TIME_FOR_THREADS_TO_STOP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		List<PostObject> shipments = postOffice.getAllShipmentsByDate(new Date());
		
		for (PostObject postObject : shipments) {
			System.out.println(postObject);
		}
		
		p.interrupt();
		System.out.println("Letters percent for today: " + postOffice.getLettersPercentForDay() + "%");
		System.out.println("Breakable packages percent: " + postOffice.getBreakablePercent() + "%");
		postOffice.showPostmenWorkDone();
	}

}
