package courier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class Company {

	private String city;
	private static HashMap<Protokol, ArrayList<MailItem>> protokolList = new HashMap<Protokol, ArrayList<MailItem>>();

	public Company(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public abstract LinkedList<MailItem> takeFromWarehouse(String destination);

	public abstract void addToWarehouse(MailItem mail);

	public abstract void setAcceptingMail(LocalEmployee le);

	public abstract LocalEmployee getAcceptingMail();

	public abstract void bringToCustomer(Person localEmployee);

	public void addToWarehouseMany(LinkedList<MailItem> mails) {
		if (mails == null) {
			try {
				throw new Exception("Invalid mail list");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (MailItem mailItem : mails) {
			addToWarehouse(mailItem);
		}
	}

	public synchronized void addProtokolMail(Protokol protokol, MailItem mailItem) {
		if (!protokolList.containsKey(protokol)) {
			protokolList.put(protokol, new ArrayList<MailItem>());
		}
//ТУК ПОНЯКОГА ГЪРМИ НУЛПОЙНТЕР А ВСИЧКО Е НАРЕД? protokolList e инициализирано най-горе, ако няма ключ в мапа слагам 2реда по-горе
		protokolList.get(protokol).add(mailItem);
	}

}
