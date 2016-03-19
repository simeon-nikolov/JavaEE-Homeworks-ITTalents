package smartcourier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class Office implements Runnable, Iterable<Office> {
	protected static final int TIME_BETWEEN_TRANSPORTING = 2000;
	private static final int EMPLOYEES_COUNT = 4;
	
	private static List<Office> offices;
	private static Office centralOffice;
	private static Map<Protocol, Protocol> protocols; // [protocol : protocol] - for faster finding (contains, get...)
	
	private List<Employee> officeEmployees;
	private List<Employee> transportEmployees;
	private List<Shipment> shipmentsInOffice; // synchronized list...
	private List<Shipment> deliveredShipments; // synchronized list...
	private City city;
	
	static {
		Office.offices = new ArrayList<Office>();
		Office.protocols = new ConcurrentHashMap<Protocol, Protocol>(); // synchronized
	}
	
	public Office(City city, boolean isCentral) {
		this.city = city;
		this.officeEmployees = new ArrayList<Employee>();
		this.transportEmployees = new ArrayList<Employee>();
		this.shipmentsInOffice = Collections.synchronizedList(new ArrayList<Shipment>()); // synchronize it
		this.deliveredShipments = Collections.synchronizedList(new ArrayList<Shipment>()); // synchronize it
		
		if (isCentral) {
			Office.setCentralOffice(this);
		} else {
			Office.offices.add(this);
		}
		
		this.assignEmployeesInOffice(isCentral);
	}
	
	private void assignEmployeesInOffice(boolean isCentral) {
		for (int officeEmployeeIndex = 1; officeEmployeeIndex <= EMPLOYEES_COUNT; officeEmployeeIndex++) {
			Address address = new Address(this.city, "Somewhere street 5");
			Employee employee = new Employee(("OEmployee" + this.city + officeEmployeeIndex), "0123456", address, this.city);
			this.officeEmployees.add(employee);
		}
		
		for (int transportEmployeeIndex = 1; transportEmployeeIndex <= EMPLOYEES_COUNT; transportEmployeeIndex++) {
			Address address = new Address(this.city, "Somewhere street 5");
			City city = isCentral ? Arrays.asList(City.values()).get(transportEmployeeIndex) : Office.getCentralOffice().getCity();
			Employee employee = new Employee(("TEmployee" + this.city + transportEmployeeIndex), "0123456", address, city);
			this.transportEmployees.add(employee);
		}
	}

	public static Office getCentralOffice() {
		return Office.centralOffice;
	}
	
	protected static Office getOfficeByCity(City city) {
		Office result = null;
		
		for (Office office : Office.offices) {
			if (office.getCity().equals(city)) {
				result = office;
				break;
			}
		}
		
		return result;
	}

	public static void setCentralOffice(Office centralOffice) {
		if (centralOffice != null) {
			Office.centralOffice = centralOffice;
		}
	}

	public City getCity() {
		return city;
	}
	
	public void addShipment(Shipment shipment, Person from) {
		if (shipment != null) {
			List<Shipment> list = new LinkedList<Shipment>();
			list.add(shipment);
			this.addShipments(list, from, false);
			System.out.println("Shipment " + shipment.getId() + " " + shipment.getContent() + 
					" from " + shipment.getSender() + " to " + shipment.getReceiver() + 
					" was added in the office in " + this.city);
		}
	}

	public void addShipments(List<Shipment> shipments, Person from, boolean areDelivered) {
		if (shipments != null && from != null) {
			Employee employee = this.getFreeOfficeEmployee();
			
			for (Shipment shipment : shipments) {
				if (areDelivered) {
					this.deliveredShipments.add(shipment);
				} else {
					this.shipmentsInOffice.add(shipment);
				}
				
				this.generateProtocol(from, employee, shipment);
				shipment.setInEmployee(employee);
				shipment.setInCity(this.city);
			}
			
			employee.setBusy(false);
			
			if (areDelivered) {
				System.out.println(shipments.size() + " shipments were delivered to the office in " +
						this.city + " from " + from);
			} else {
				System.out.println(shipments.size() + " shipments were added to the office in " + 
						this.city + " from " + from);
			}
		}
	}
	
	private Employee getFreeOfficeEmployee() {
		Employee employee = null;
		
		do {
			employee = this.officeEmployees.get((int)(Math.random() * this.officeEmployees.size()));
		} while (employee.isBusy());
		
		employee.setBusy(true);
		
		return employee;
	}
	
	private Employee getFreeTransportEmployee() {
		Employee employee = null;
		
		do {
			employee = this.transportEmployees.get((int)(Math.random() * this.transportEmployees.size()));
		} while (employee.isBusy());
		
		employee.setBusy(true);
		
		return employee;
	}
	
	public Employee getTransportEmployeeByCity(City city) {
		Employee transportEmployee = null;
		
		for (Employee employee : this.transportEmployees) {
			if (employee.getCity().equals(city)) {
				transportEmployee = employee;
				break;
			}
		}
		
		return transportEmployee;
	}
	
	protected void generateProtocol(Person from, Person to, Shipment shipment) {
		Protocol protocol = new Protocol(from, to);
		
		if (!Office.protocols.containsKey(protocol)) {
			Office.protocols.put(protocol, protocol);
		} else {
			protocol = Office.protocols.get(protocol);
		}
		
		protocol.addShipment(shipment);
	}
	
	private void transportShipmentsToCentralOffice(List<Shipment> shipments, Employee transportEmployee) {
		Office.centralOffice.addShipments(shipments, transportEmployee, false);
		transportEmployee.setBusy(false);
	}
	
	protected List<Shipment> takeAllOfficeShipments() {
		List<Shipment> result = new ArrayList<Shipment>();
		
		synchronized (this.shipmentsInOffice) {
			result.addAll(this.shipmentsInOffice);
			this.shipmentsInOffice.clear();
		}
		
		return result;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(TIME_BETWEEN_TRANSPORTING);
			} catch (InterruptedException e) {
				return;
			}
			
			List<Shipment> shipments = this.takeAllOfficeShipments();
			Employee transportEmployee = this.getFreeTransportEmployee();
			
			for (Shipment shipment : shipments) {
				Employee lastInEmployee = shipment.getInEmployee();
				this.generateProtocol(lastInEmployee, transportEmployee, shipment);
				shipment.setInEmployee(transportEmployee);
			}
			
			if (shipments.size() > 0) {
				this.transportShipmentsToCentralOffice(shipments, transportEmployee);
			}
			
			synchronized (this.deliveredShipments) {
				for (Shipment shipment : this.deliveredShipments) {
					this.deliverShipmentToPerson(shipment);
				}
				
				this.deliveredShipments.clear();
			}
		}
	}

	private void deliverShipmentToPerson(Shipment shipment) {
		System.out.println("Shipment " + shipment.getId() + " with content: " + shipment.getContent() + 
				" was delivered to " + shipment.getReceiver().getName() + " in " + shipment.getReceiver().getAddress().getCity());
		shipment.setIsDelivered(true);
		this.generateProtocol(shipment.getInEmployee(), shipment.getReceiver(), shipment);
	}

	@Override
	public Iterator<Office> iterator() {
		return Office.offices.iterator();
	}
	
	public static void getShipmentInfo(int id) {
		Shipment shipment = null;
		boolean found = false;
		
		for (Protocol protocol : Office.protocols.keySet()) {
			for (Shipment shipmentInProtocol : protocol) {
				if (shipmentInProtocol.getId() == id) {
					shipment = shipmentInProtocol;
					found = true;
					break;
				}
			}
			
			if (found) {
				break;
			}
		}
		
		if (!found) {
			System.out.println("There's no shipment with such id!");
		} else {
			System.out.println("Shipment " + shipment.getId() + " with contents " + shipment.getContent() +
					" sender: " + shipment.getSender() + ", receiver: " + shipment.getReceiver() +
					" is " + (shipment.getIsDelivered() ? "delivered" : "not delivered") + " and last was in employee: " +
					shipment.getInEmployee() + " and was in office city: " + shipment.getInCity());
		}
	}
	
	public static void listProtocolsByShipment(int id) {
		List<Protocol> protocols = new LinkedList<Protocol>();
		
		for (Protocol protocol : Office.protocols.keySet()) {
			for (Shipment shipmentInProtocol : protocol) {
				if (shipmentInProtocol.getId() == id) {
					protocols.add(protocol);
					break;
				}
			}
		}
		
		for (Protocol protocol : protocols) {
			System.out.println(protocol);
		}
	}
	
	public static void listClients() {
		System.out.println("Clients:");
		SortedSet<Person> clients = new TreeSet<Person>((c1, c2) -> (
				(c1.getName().compareTo(c2.getName()) == 0 ? 
						c1.getEgn().compareTo(c2.getEgn()) : 
							c1.getName().compareTo(c2.getName()))));
		
		for(Protocol protocol : Office.protocols.keySet()) {
			Person person = protocol.getFrom();
			
			if (!(person instanceof Employee)) {
				clients.add(person);
			}
		}
		
		for(Person client : clients) {
			System.out.println(client);
		}
	}
	
	public static void listShipmentsByDatetime() {
		System.out.println("Shipments by time entered in the system:");
		SortedSet<Shipment> shipments = new TreeSet<Shipment>((s1, s2) -> 
			((s1.getDateTimeOfSending().compareTo(s2.getDateTimeOfSending())) == 0
					? s1.getId() - s2.getId()  : 
						s1.getDateTimeOfSending().compareTo(s2.getDateTimeOfSending())));
		
		for(Protocol protocol : Office.protocols.keySet()) {
			for(Shipment shipment : protocol) {
				shipments.add(shipment);
			}
		}
		
		for(Shipment shipment : shipments) {
			System.out.println(shipment);
		}
	}
}
