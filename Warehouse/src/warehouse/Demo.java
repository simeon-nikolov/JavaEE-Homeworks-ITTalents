package warehouse;
import java.util.ArrayList;
import java.util.List;

import exceptions.WarehouseInvalidArgumentException;

// Simeon Nikolov <- za po-lesno ;)

// znam, che moje i bez klasa Product i da si napravq napravo Map<String, Integer>,
// ama taka pyk imam po-golqma abstrakciq i extensibility (pri nujda :D).
// Pyk i me myrzi da go prepravqm sega sled kato sym go napravil taka. :D
// A pyk tiq tipove - FRUITS, VEGETABLES i MEATS izobshto ne razbrah zashto trqbva da gi ima? :D
// Vseki ot klientite kupuva po 50 prudukta (pone) i nakraq vsi4ki nishki gi spiram.
// Reshih da ne polzvam thread pool, zashtoto ne moga da si zadam imena na nishkite.
// Golqma pletenica. Good Luck!

public class Demo {

	private static final int CLIENTS_COUNT = 9;

	public static void main(String[] args) throws WarehouseInvalidArgumentException {
		IWarehouse warehouse = new Warehouse();
		ISupplier supplier = new Supplier(warehouse);
		new Thread(supplier).start();
		List<IShop> shops = new ArrayList<IShop>();
		shops.add(new Shop(warehouse, "Shop A"));
		shops.add(new Shop(warehouse, "Shop B"));
		shops.add(new Shop(warehouse, "Shop C"));
		
		for (IShop shop : shops) {
			new Thread(shop, shop.getShopName()).start();
		}
		
		for (int i = 0; i < CLIENTS_COUNT; i++) {
			int index = i / shops.size();
			IClient client = new Client("Client " + (i + 1), shops.get(index));
			new Thread(client, client.getName()).start();
		}
		
		while (Client.getFinishedClientsCount().get() < CLIENTS_COUNT) {
			// wait for all clients to finish
		}
		
		for (IShop shop : shops) {
			shop.stop();
		}
		
		while (Shop.getShopsFinishedWorking().get() < shops.size()) {
			// wait for all shops to finish before stopping the supplier to avoid chance for
			// unwanted dead-lock
		}
		
		synchronized (warehouse) {
			supplier.stop();
			warehouse.notifyAll(); // wake up the supplier so it can finish too
		}
		
		System.out.println("BYE");
		// abe raboti ama dali e vqrno samo edin gospod znae i ti shte kajesh :D
	}

}
