package smartcourier;

import java.util.Date;

public class Shipment {
	private static int count = 0;
	
	private int id;
	private String content;
	private Person sender;
	private Person receiver;
	private Date dateTimeOfSending;
	private Employee inEmployee;
	private City inCity;
	private boolean isDelivered;
	
	public Shipment(String content, Person sender, Person receiver) {
		this.dateTimeOfSending = new Date();
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.id = ++count;
	}

	public Employee getInEmployee() {
		return inEmployee;
	}

	public void setInEmployee(Employee inEmployee) {
		this.inEmployee = inEmployee;
	}

	public City getInCity() {
		return inCity;
	}

	public void setInCity(City inCity) {
		this.inCity = inCity;
	}

	public boolean getIsDelivered() {
		return isDelivered;
	}

	public void setIsDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public int getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Person getSender() {
		return sender;
	}

	public Person getReceiver() {
		return receiver;
	}

	public Date getDateTimeOfSending() {
		return dateTimeOfSending;
	}

	@Override
	public String toString() {
		return "Shipment [id=" + id + ", content=" + content + ", sender=" + sender + ", receiver=" + receiver + ", dateTimeOfSending=" + dateTimeOfSending + ", inEmployee=" + inEmployee + ", inCity=" + inCity + ", isDelivered=" + isDelivered + "]";
	}
}
