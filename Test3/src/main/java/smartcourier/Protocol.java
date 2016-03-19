package smartcourier;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Protocol implements Iterable<Shipment> {
	private Person from;
	private Person to;
	private List<Shipment> shipments;
	
	public Protocol(Person from, Person to) {
		this.from = from;
		this.to = to;
		this.shipments = new LinkedList<Shipment>();
	}
	
	public void addShipment(Shipment shipment) {
		if (shipment != null) {
			this.shipments.add(shipment);
			
			if (this.to instanceof Employee) {
				Employee employee = (Employee) this.to;
				employee.accountShipment(shipment);
			}
		}
	}

	public Person getFrom() {
		return from;
	}

	public Person getTo() {
		return to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Protocol other = (Protocol) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public Iterator<Shipment> iterator() {
		return this.shipments.iterator();
	}

	@Override
	public String toString() {
		return "Protocol [from=" + from.getName() + ", to=" + to.getName() + "]";
	}
}
