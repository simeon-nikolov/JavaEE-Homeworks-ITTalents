package courier;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

public class Office extends Company {
	private LocalEmployee acceptingMail;
	private ArrayBlockingQueue<MailItem> localWarehouse;
	private ArrayBlockingQueue<MailItem> finalDeliveryToCustomer;
	private CentralWarehouse centralRepo;

	public Office(String city, CentralWarehouse centralRepo) {
		super(city);
		localWarehouse = new ArrayBlockingQueue<MailItem>(10);
		finalDeliveryToCustomer = new ArrayBlockingQueue<MailItem>(10);
		this.centralRepo = centralRepo;
		this.centralRepo.registerOfficeInWarehouse(this);

	}

	public void addToWarehouse(MailItem mail) {
		if (mail == null) {
			try {
				throw new MailException("Invalid mail items");
			} catch (MailException e) {
				e.printStackTrace();
			}
		}
		if (mail.getReceiver().getAddress().getCity() != this.getCity()) {
			try {
				this.localWarehouse.put(mail);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			this.finalDeliveryToCustomer.add(mail);
		}
	}

	@Override
	public LinkedList<MailItem> takeFromWarehouse(String destination) {
		LinkedList<MailItem> temp = new LinkedList<MailItem>();
		try {
			temp.add(localWarehouse.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public LocalEmployee getAcceptingMail() {
		return acceptingMail;
	}

	public void setAcceptingMail(LocalEmployee acceptingMail) {
		this.acceptingMail = acceptingMail;
	}

	public void bringToCustomer(Person localEmployee){
		while(true){
			MailItem currentOne = null;
			try {
				currentOne = finalDeliveryToCustomer.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" Customer "+ currentOne.getReceiver().getFname()+ " " +
			currentOne.getReceiver().getLname()+ " received his item!");
			Protokol protokol = new Protokol(localEmployee, currentOne.getReceiver(), LocalDateTime.now());
			currentOne.setOwner(currentOne.getReceiver());
			currentOne.addProtokolForMailItem(protokol);
			currentOne.setDelivered(true);
			}
		}


// public LinkedList<MailItem> takeFromWarehouse() {
// LinkedList<MailItem> temp = new LinkedList<MailItem>();
// while(this.localWarehouse.size() != 0){
// try {
// temp.add(localWarehouse.take());
// } catch (InterruptedException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// }
//
// return temp;
// }
// public LinkedList<MailItem> takeAllFofDelivery() {
// LinkedList<MailItem> temp = new LinkedList<MailItem>();
// synchronized (localWarehouse) {
// temp.addAll(localWarehouse);
// this.localWarehouse.clear();
// }
// return temp;
// }
//
// public boolean waitingForDelivery() {
// if (localWarehouse.size() != 0) {
// return true;
// }
// return false;
// }
//
//
// public void acceptListWithMailFromCentral(LinkedList<MailItem> mail)
// throws MailException {
// if (mail == null) {
// throw new MailException("Invalid mail item");
// }
// synchronized (waitingForDelivery) {
// this.waitingForDelivery.addAll(mail);
// }
// }
}
