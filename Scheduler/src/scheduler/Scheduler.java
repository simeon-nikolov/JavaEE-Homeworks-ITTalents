package scheduler;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	private Queue<Task> taskQueue;
	
	public Scheduler() {
		this.taskQueue = new LinkedList<Task>();
	}
	
	public void push(Task task) {
		if (task != null) {
			this.taskQueue.add(task);
		}
	}
	
	public static void main(String[] args) {
		Scheduler taskScheduler = new Scheduler();
		taskScheduler.push(new GardenTask());
		taskScheduler.push(new ShoppingTask());
		taskScheduler.push(new CookingTask());
		taskScheduler.push(new HomeworkTask());
		
		for (Task task : taskScheduler.taskQueue) {
			task.doWork();;
		}
	}
}
