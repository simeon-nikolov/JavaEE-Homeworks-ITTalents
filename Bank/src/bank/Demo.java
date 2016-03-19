package bank;

import java.util.ArrayList;

import exceptions.InvalidArgumentException;

public class Demo {
	private static final int NUMBER_OF_CLIENTS = 10;

	public static void main(String[] args) {
		// 5.
		Bank bnb = null;
		
		try {
			bnb = new Bank("BNB", 10_000.0);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		// 6.
		ArrayList<Client> clients = new ArrayList<Client>();
		
		for (int index = 0; index < NUMBER_OF_CLIENTS; index++) {
			double money = getRandom(500, 7000);
			double salary = getRandom(380, 5000);
			Client client = null;
			
			try {
				client = new Client("ClientName" + index, "Some address " + index, money, salary, bnb);
			} catch (InvalidArgumentException e) {
				e.printStackTrace();
			}
			
			clients.add(client);
		}
		
		for (Client client : clients) {
			double percent = getRandom(80, 100) / 100.0;
			double moneyToDeposit = client.getMoney() * percent;
			Deposit deposit = null;
			
			try {
				deposit = new Deposit("Deposit for " + client.getName(), 5, 6, moneyToDeposit);
			} catch (InvalidArgumentException e) {
				e.printStackTrace();
			}
			
			client.openDeposit(deposit);
		}
		
		// 8.
		System.out.printf("Bank money: %.2f\n", bnb.getAvaibleMoney());
		System.out.printf("Bank reserve: %.2f\n", bnb.getBankReserve());
		
		// 9.
		for (Client client : clients) {
			double money = getRandom(4800, 5000);
			Credit credit = null;
			
			try {
				credit = creditMaker(money);
			} catch (InvalidArgumentException e) {
				e.printStackTrace();
			}
			
			client.applyForCredit(credit);
		}
		
		// 10.
		System.out.println("\nAll bank clients:\n");
		
		for (Client client : clients) {
			System.out.println(client);
		}
		
		System.out.println("\nAll deposits in the bank:\n");
		bnb.printDeposits();
		System.out.println("\nAll credits in the bank:\n");
		bnb.printCredits();
		System.out.println("\nBank info:\n");
		System.out.println(bnb.toString());
	}
	
	private static double getRandom(double min, double max) {
		return Math.random() * (max - min) + min;
	}
	
	private static Credit creditMaker(double money) throws InvalidArgumentException {
		if (money > 1000) {
			return new ConsumerCredit("Consumer Credit", 30, money);
		} else {
			return new HomeCredit("Home Credit", 12, money);
		}
	}
}
