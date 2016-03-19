package courier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MailItem {
private Customer sender;
private Customer receiver;
private boolean isDelivered;
private Person owner;
private String content;
private LocalDateTime acceptedWhen;
private int id;
private static int totalMailItems;
private ArrayList<Protokol> protokolsForThisItem;
private static HashMap<Integer, MailItem> mailItemList = new HashMap<Integer, MailItem>();

public MailItem(Customer sender, Customer receiver, String content) {
	this.isDelivered = false;
	this.acceptedWhen = LocalDateTime.now();
	synchronized (MailItem.class) {
		totalMailItems++;
		this.id = totalMailItems;
	}

	this.protokolsForThisItem = new ArrayList<Protokol>();
	this.sender = sender;
	this.receiver = receiver;
	this.content = content;
	MailItem.mailItemList.put(this.id, this);
	this.acceptedWhen = LocalDateTime.now();
}
public void setDelivered(boolean isDelivered) {
	this.isDelivered = isDelivered;
}
public void setOwner(Person owner) {
	this.owner = owner;
}
public Customer getSender() {
	return sender;
}
public Customer getReceiver() {
	return receiver;
}
public boolean isDelivered() {
	return isDelivered;
}
public Person getOwner() {
	return owner;
}
public String getContent() {
	return content;
}
public LocalDateTime getAcceptedWhen() {
	return acceptedWhen;
}
public int getId() {
	return id;
}
public void addProtokolForMailItem(Protokol protokol){
	this.protokolsForThisItem.add(protokol);
}

}
