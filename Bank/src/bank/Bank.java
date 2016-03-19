package bank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import exceptions.InvalidArgumentException;

public class Bank {
	private String name;
	private double avaibleMoney;
	private double bankReserve;
	private Map<Client, Set<BankProduct>> clients; // clients : bankProducts

	public Bank(String name, double avaibleMoney) throws InvalidArgumentException {
		this.setName(name);
		this.setAvaibleMoney(avaibleMoney);
		this.bankReserve = this.avaibleMoney * 0.1;
		this.clients = new HashMap<Client, Set<BankProduct>>();
	}
	
	public boolean acceptDeposit(Client client, Deposit deposit) {
		boolean isAccepted = false;
		
		if (client.getMoney() - deposit.getMoneyAmount() >= 0) {
			this.avaibleMoney += deposit.getMoneyAmount();
			this.bankReserve += deposit.getMoneyAmount() * 0.9;
			
			try {
				client.setMoney(client.getMoney() - deposit.getMoneyAmount());
			} catch (InvalidArgumentException e) {
				e.printStackTrace();
			}
			
			if (!this.clients.containsKey(client)) {
				Set<BankProduct> products = new HashSet<BankProduct>();
				this.clients.put(client, products);
			}
			
			Set<BankProduct> products = this.clients.get(client);
			products.add(deposit);
			isAccepted = true;
		}
		
		return isAccepted;
	}
	
	public void payDepositsInterests() {
		for (Client client : this.clients.keySet()) {
			Set<BankProduct> products = this.clients.get(client);
			
			for (BankProduct bankProduct : products) {
				if (bankProduct instanceof Deposit) {
					Deposit deposit = (Deposit) bankProduct;
					double depositMoney = deposit.getMoneyAmount() + deposit.calculateDepositInterest();
					
					try {
						deposit.setMoneyAmount(depositMoney);
					} catch (InvalidArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public boolean grantCredit(Client client, Credit credit) {
		boolean isGranted = false;
		
		if (client.getMonthlyCreditPayments() < client.getSalary() * 0.5) {
			if (this.avaibleMoney - credit.getMoneyAmount() > this.getDepositsSum() * 0.1) {
				this.avaibleMoney -= credit.getMoneyAmount();
				
				try {
					client.setMoney(client.getMoney() + credit.getMoneyAmount());
				} catch (InvalidArgumentException e) {
					e.printStackTrace();
				}
				
				if (!this.clients.containsKey(client)) {
					Set<BankProduct> products = new HashSet<BankProduct>();
					this.clients.put(client, products);
				}
				
				Set<BankProduct> products = this.clients.get(client);
				products.add(credit);
				isGranted = true;
			}
		}
		
		return isGranted;
	}
	
	private double getDepositsSum() {
		double sum = 0.0;
		
		for (Client client : this.clients.keySet()) {
			Set<BankProduct> products = this.clients.get(client);
			
			for (BankProduct bankProduct : products) {
				if (bankProduct instanceof Deposit) {
					Deposit deposit = (Deposit) bankProduct;
					sum += deposit.getMoneyAmount();
				}
			}
		}
		
		return sum;
	}

	public void printDeposits() {
		for (Client client : this.clients.keySet()) {
			Set<BankProduct> products = this.clients.get(client);
			
			for (BankProduct bankProduct : products) {
				if (bankProduct instanceof Deposit) {
					System.out.println(bankProduct);
				}
			}
		}
	}
	
	public void printCredits() {
		for (Client client : this.clients.keySet()) {
			Set<BankProduct> products = this.clients.get(client);
			
			for (BankProduct bankProduct : products) {
				if (bankProduct instanceof Credit) {
					System.out.println(bankProduct);
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidArgumentException {
		if (name == null) {
			throw new InvalidArgumentException("Name can't be null!");
		}
		
		this.name = name;
	}

	public double getAvaibleMoney() {
		return avaibleMoney;
	}

	public void setAvaibleMoney(double avaibleMoney) throws InvalidArgumentException {
		if (avaibleMoney < 0) {
			throw new InvalidArgumentException("Avaible money can't be negative value!");
		}
		
		this.avaibleMoney = avaibleMoney;
	}
	
	public double getBankReserve() {
		return this.bankReserve;
	}

	@Override
	public String toString() {
		String avaibleMoney = String.format("%.2f", this.avaibleMoney);
		String bankReserve = String.format("%.2f", this.bankReserve);
		int clients = this.clients.size();
		int bankProducts = 0;
		
		for (Client client : this.clients.keySet()) {
			bankProducts += this.clients.get(client).size();
		}
		
		return "Bank Name=" + name + ", Avaible Money=" + avaibleMoney
				+ ", Bank Reserve=" + bankReserve + ", clients=" + clients + ", Bank Products=" + bankProducts;
	}
}
