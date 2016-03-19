package courier;

import java.time.LocalDateTime;
import java.util.TreeSet;

public class LocalEmployee extends Person implements Runnable{

	private Company companyLocation;

	public LocalEmployee(String fname, String lname, long customerNumber, String tel, Company companyLocation) {
		super(fname, lname, customerNumber, tel);
		this.companyLocation = companyLocation;
		this.companyLocation.setAcceptingMail(this);
	}
	
	@Override
	public void run() {
		while(true){
			this.companyLocation.bringToCustomer(this);
		}
		
	}
	
	
	
	
	
	public void acceptFromCustomer(MailItem mail) {
		this.companyLocation.addToWarehouse(mail);
		Protokol protokol = new Protokol(mail.getSender(), this, LocalDateTime.now());
		mail.setOwner(this);
		mail.addProtokolForMailItem(protokol);
	}



	// public void acceptSingleMail(MailItem mail) throws MailException{
	// acceptListWithMail((LinkedList<MailItem>) Arrays.asList(mail));
	// }

}
