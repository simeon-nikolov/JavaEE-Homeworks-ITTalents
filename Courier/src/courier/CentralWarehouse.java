package courier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

public class CentralWarehouse extends Company{
	private HashMap<String, ArrayBlockingQueue<MailItem>> warehouse;
	private LocalEmployee acceptingMail;
	
	public CentralWarehouse(String city) {
		super(city);
		this.warehouse = new HashMap<String, ArrayBlockingQueue<MailItem>>();
	}

	public void registerOfficeInWarehouse(Office newOffice){
		if(newOffice != null && !warehouse.containsKey(newOffice)){
		this.warehouse.put(newOffice.getCity(), new ArrayBlockingQueue<MailItem>(10));}
		else{try {
			throw new MailException("Invalid office or office already exists");
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	}
	
	public void addToWarehouse(MailItem mail) {
		if (mail == null) {
			try {
				throw new MailException("Invalid mail items");
			} catch (MailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String city = mail.getReceiver().getAddress().getCity();
//		if(!this.warehouse.containsKey(city)){
//			this.warehouse.put(city, new ArrayBlockingQueue<MailItem>(10));
//		}
		try {
			this.warehouse.get(city).put(mail);
			synchronized (this.warehouse.get(city)) {
				this.warehouse.get(city).notifyAll();
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public LinkedList<MailItem> takeFromWarehouse(String destination){
		if(destination == null){try {
			throw new MailException("Invalid destination");
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
//		while(!this.warehouse.containsKey(destination)){
//			synchronized (destination) {
//				Thread.currentThread().wait();
//			}
//			
//			}
		LinkedList<MailItem> temp = new LinkedList<MailItem>();
		try {
//-----------------------Tuk dolu se 4upi-------------------------------Veche maj ne
			temp.add(this.warehouse.get(destination).take());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}



	@Override
	public void setAcceptingMail(LocalEmployee le) {
		this.acceptingMail = acceptingMail;
		
	}

	@Override
	public LocalEmployee getAcceptingMail() {
		return this.acceptingMail;
	}



	@Override
	public void bringToCustomer(Person localEmployee) {
		// TODO Auto-generated method stub
		
	}






	



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public void acceptListWithMail(LinkedList<MailItem> mailFromLocal) throws MailException {
//		if (mailFromLocal == null) {
//			throw new MailException("Invalid mail items");
//		}
//			for (MailItem mail : mailFromLocal) {
//				String destination = mail.getReceiver().getAddress().getCity();
//				if (!this.warehouse.containsKey(destination)) {
//					this.warehouse.put(destination, new ArrayBlockingQueue<MailItem>(10));
//				}
//				try {
//					this.warehouse.get(destination).put(mail);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//	}
//
//	public LinkedList<MailItem> takeAllFofDelivery(Company deliverTo) throws MailException {
//		if(deliverTo == null){throw new MailException("Invalid destination");}
//			LinkedList<MailItem> temp = new LinkedList<MailItem>();
//			if(!this.warehouse.containsKey(deliverTo)){return temp;}
//			try {
//				while(this.warehouse.get(deliverTo).size() !=0){
//				temp.add(this.warehouse.get(deliverTo).take());}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//
//		return temp;
//	}
//
//	public boolean needsDelivery(Company deliverTo){
//		if(warehouse.get(deliverTo).size()>0){
//			return true;
//		}return false;
//	}
	
}
