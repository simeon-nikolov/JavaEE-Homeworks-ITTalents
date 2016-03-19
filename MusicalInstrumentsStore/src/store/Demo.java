package store;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import exceptions.InvalidValueException;

public class Demo {
	private static final int NUMBER_OF_THREADS = 5;
	private static final int TIME_TO_WAIT_THE_CLIENTS_TO_BUY = 60_000;
	private static final int INITIAL_SUPPLY_COUNT = 15;

	public static void main(String[] args) {
		Store store = new Store();
		
		// initial store supplying
		for (String name : Store.INSTRUMENT_NAMES) {
			try {
				store.addInstruments(name, INITIAL_SUPPLY_COUNT);
			} catch (InvalidValueException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("=========== By type: ===========");
		store.listIntrumentsByType();
		System.out.println("=========== By name: ===========");
		store.listInstrumentsByName();
		System.out.println("=========== By price ascending: ===========");
		store.listInstrumentsByPrice(true);
		System.out.println("=========== By price descending: ===========");
		store.listInstrumentsByPrice(false);
		System.out.println("=========== Avaible: ===========");
		store.listAvaibleInstruments();
		System.out.println("=========== Instruments by sold quantity: ===========");
		store.listSoldInstrumentsByQuantity();
		System.out.println("=========== Sum of all sales: ===========");
		System.out.println(store.getSumOfAllSales());
		System.out.println("=========== Most sold instruments: ===========");
		store.listMostSoldInstruments();
		System.out.println("=========== Least sold instruments: ===========");
		store.listLeastSoldInstruments();
		System.out.println("=========== Most sellable types: ===========");
		store.listMostSoldTypes();;
		System.out.println("=========== Most profitable type: ===========");
		store.listMostProfitableTypes();
		
		// create 10 clients and make them shop
		ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		
		for (int clientNumber = 1; clientNumber <= 10; clientNumber++) {
			try {
				threadPool.submit(new Thread(new Client("Client " + clientNumber, store)));
			} catch (InvalidValueException e) {
				e.printStackTrace();
			}
		}
		
		// wait some time for the clients to buy stuff
		try {
			Thread.sleep(TIME_TO_WAIT_THE_CLIENTS_TO_BUY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// stop the clients from buying
		threadPool.shutdownNow();
		
		// print statistics
		System.out.println("=========== Sum of all sales: ===========");
		System.out.println(store.getSumOfAllSales());
		System.out.println("=========== Most sold instruments: ===========");
		store.listMostSoldInstruments();
		System.out.println("=========== Least sold instruments: ===========");
		store.listLeastSoldInstruments();
		System.out.println("=========== Most sellable types: ===========");
		store.listMostSoldTypes();;
		System.out.println("=========== Most profitable type: ===========");
		store.listMostProfitableTypes();	
	}

}
