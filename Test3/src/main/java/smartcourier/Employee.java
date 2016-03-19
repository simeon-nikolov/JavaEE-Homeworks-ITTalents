package smartcourier;

import java.util.LinkedList;
import java.util.List;

public class Employee extends Person {
	private boolean isBusy;
	private City city;
	private List<Shipment> shipmentsDone;

	public Employee(String name, String phoneNumber, Address address, City city) {
		super(name, phoneNumber, address);
		this.city = city;
		this.shipmentsDone = new LinkedList<Shipment>();
	}
	
	public void accountShipment(Shipment shipment) {
		if (shipment != null) {
			this.shipmentsDone.add(shipment);
		}
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public City getCity() {
		return city;
	}
}
