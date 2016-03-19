package postoffice;

import java.util.ArrayList;
import java.util.List;

public class Postman extends Person implements Runnable {
	private int yearsWork;
	private List<PostObject> shipmentsToSend;
	private int shipmentsSent;
	private boolean interupted;

	public Postman(String firstName, String lastName, int yearsWork, PostOffice postOffice) {
		super(firstName, lastName, postOffice);
		this.yearsWork = yearsWork;
		this.shipmentsToSend = new ArrayList<PostObject>();
		this.shipmentsSent = 0;
		this.interupted = false;
		postOffice.registerPostman(this);
	}
	
	public int getYearsWork() {
		return yearsWork;
	}
	
	public void setShipmentsToSend(List<PostObject> postObjects) {
		synchronized (this.shipmentsToSend) {
			this.shipmentsToSend.addAll(postObjects);
			this.shipmentsToSend.notifyAll();
		}
	}
	
	@Override
	public void run() {
		while (!this.interupted) {
			while (this.shipmentsToSend.isEmpty()) {
				synchronized (this.shipmentsToSend) {
					try {
						this.shipmentsToSend.wait();
					} catch (InterruptedException e) {
						System.out.println(super.getFirstName() + " will now stop working.");
						return;
					}
				}
			}
			
			synchronized (this.shipmentsToSend) {
				this.sendShipments(this.shipmentsToSend);
				this.shipmentsToSend.clear();
			}
		}
	}

	public void sendShipments(List<PostObject> postObjects) {
		if (postObjects != null) {
			for (PostObject postObject : postObjects) {
				try {
					Thread.sleep(postObject.getTimeToSend());
				} catch (InterruptedException e) {
					System.out.println(super.getFirstName() + " will now stop working.");
					this.interupted = true;
					return;
				}
				
				System.out.println("Postman " + super.getFirstName() + " sent out " + postObject);
				this.shipmentsSent++;
			}
		}
	}
	
	public int getShipmentsSentOutCount() {
		return this.shipmentsSent;
	}
}
