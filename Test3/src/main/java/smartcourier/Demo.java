package smartcourier;

import java.util.ArrayList;
import java.util.List;

// Само не съм навързал DAO-то към логиката.

public class Demo {
	private static final int TIME_BEFORE_STATISTICS = 7000;
	private static final String CITY_OFFICES_THREAD_GROUP = "City Offices";
	private static final String[] CONTENTS = {"kartofi", "PC", "MAC", "Laptop", "Pulover", "Ogledalo za kola", "Video karta", "kolelo"};
	
	public static void main(String[] args) {
		try {
			CentralOffice central = new CentralOffice();
			Thread centralOfficeThread = new Thread(central);
			centralOfficeThread.start();
			ThreadGroup cityOffices = new ThreadGroup(CITY_OFFICES_THREAD_GROUP);
			List<Office> offices = new ArrayList<Office>();
			
			for (City city : City.values()) {
				if (!city.equals(CentralOffice.CENTRAL_CITY)) {
					Office office = new Office(city, false);
					offices.add(office);
					new Thread(office, CITY_OFFICES_THREAD_GROUP).start();
				}
			}
			
			sendShipments(Office.getOfficeByCity(City.VARNA), Office.getOfficeByCity(City.SOFIA), 4);
			sendShipments(Office.getOfficeByCity(City.VARNA), Office.getOfficeByCity(City.PLOVDIV), 2);
			sendShipments(Office.getOfficeByCity(City.VARNA), Office.getOfficeByCity(City.RUSE), 3);
			Thread.sleep(TIME_BEFORE_STATISTICS);
			int shipmentId = 4;
			Office.getShipmentInfo(shipmentId);
			System.out.println("Protocols for shipment with id " + shipmentId + ":");
			Office.listProtocolsByShipment(shipmentId);
			Office.listClients();
			Office.listShipmentsByDatetime();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong: " + e.getMessage());
		}
	}

	private static void sendShipments(Office fromOffice, Office toOffice, int count) {
		List<Shipment> shipments = new ArrayList<Shipment>(count);
		Person sender = getPersonByCity(fromOffice.getCity());
		Person receiver = getPersonByCity(toOffice.getCity());
		
		for (int index = 1; index <= count; index++) {
			String content = CONTENTS[(int)(Math.random() * CONTENTS.length)];
			Shipment shipment = new Shipment(content, sender, receiver);
			shipments.add(shipment);
		}
		
		fromOffice.addShipments(shipments, sender, false);
	}

	private static Person getPersonByCity(City city) {		
		String street = "Nowhere street " + (int)(Math.random() * 100);
		Address address = new Address(city, street);
		Person person = new Person("PersonIn" + city.toString(), "0887123456", address);
		
		return person;
	}
}
