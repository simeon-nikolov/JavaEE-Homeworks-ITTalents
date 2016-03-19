package courier;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Driver extends LocalEmployee implements Runnable, IConstants{

	private Company deliverTo;
	private Company  deliverFrom;

	public Driver(String fname, String lname, long customerNumber, String tel, Company deliverFrom, Company deliverTo) {
		super(fname, lname, customerNumber, tel, deliverFrom);
		this.deliverTo = deliverTo;
		this.deliverFrom = deliverFrom;
		if(deliverTo.getCity().equals(TURNOVO)){
		this.deliverFrom.setAcceptingMail(this);}
	}

	@Override
	public void run() {
		while(true){
			LinkedList<MailItem> itemsTaken = this.deliverFrom.takeFromWarehouse(deliverTo.getCity());
			LocalDateTime timeTaken = LocalDateTime.now();
			for(MailItem mail :itemsTaken){
				Protokol protokol = new Protokol(mail.getOwner(), this, timeTaken);
				System.out.println(Thread.currentThread().getName() +" Item "+ mail.getId()+ " moved from "+ deliverFrom.getCity() +" to "+ deliverTo.getCity());
				this.deliverFrom.addProtokolMail(protokol, mail);
				mail.setOwner(this);
				mail.addProtokolForMailItem(protokol);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.deliverTo.addToWarehouseMany(itemsTaken);
			LocalDateTime timeDelivered = LocalDateTime.now();
			for(MailItem mail :itemsTaken){
				Protokol protokol = new Protokol(this, deliverTo.getAcceptingMail(), timeDelivered);
				this.deliverFrom.addProtokolMail(protokol, mail);
				mail.setOwner(deliverTo.getAcceptingMail());
				mail.addProtokolForMailItem(protokol);
			}
			
		}
		
	}

	
	
	
}
