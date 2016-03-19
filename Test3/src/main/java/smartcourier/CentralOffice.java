package smartcourier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CentralOffice extends Office implements Runnable {
	public static final City CENTRAL_CITY = City.VELIKO_TARNOVO;

	public CentralOffice() {
		super(CENTRAL_CITY, true);
	}
	
	public void transportShipmentsToCity(List<Shipment> shipments, Employee transportEmployee) {
		Office office = super.getOfficeByCity(transportEmployee.getCity());
		System.out.println("Transporting " + shipments.size() + " shipments from central office to " + office.getCity());
		office.addShipments(shipments, transportEmployee, true);
		transportEmployee.setBusy(false);
	}
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(TIME_BETWEEN_TRANSPORTING);
			} catch (InterruptedException e) {
				return;
			}
			
			List<Shipment> shipmentsInOffice = super.takeAllOfficeShipments();
			
			for (Office office : this) {
				City city = office.getCity();
				Employee transportEmployee = super.getTransportEmployeeByCity(city);
				transportEmployee.setBusy(true);
				List<Shipment> shipmentsForCity = new ArrayList<Shipment>();
				
				for (Iterator<Shipment> it = shipmentsInOffice.iterator(); it.hasNext();) {
					Shipment shipment = it.next();
					
					if (shipment.getReceiver().getAddress().getCity().equals(city)) {
						shipmentsForCity.add(shipment);
						it.remove();
						super.generateProtocol(shipment.getInEmployee(), transportEmployee, shipment);
						shipment.setInEmployee(transportEmployee);
					}
				}
				
				if (shipmentsForCity.size() > 0) {
					this.transportShipmentsToCity(shipmentsForCity, transportEmployee);
				}
			}
		}
	}

}
