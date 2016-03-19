package bank;

import java.util.HashSet;
import java.util.Set;

import exceptions.InvalidArgumentException;

public class Client {
	private static final String SALARY_ERROR_MSG = "Salary can't be negative value!";
	private static final String MONEY_ERROR_MSG = "Money can't be negative value!";
	private static final String ADDRESS_NULL_ERROR_MSG = "Address can't be null!";
	private static final String NAME_NULL_ERROR_MSG = "Name can't be null!";
	private static final String BANK_NULL_ERROR_MSG = "Bank can't be null!";
	private String name;
	private String address;
	private double money;
	private double salary;
	private Bank bank;
	private Set<Deposit> deposits;
	private Set<Credit> credits;

	public Client(String name, String address, double money, double salary, Bank bank) throws InvalidArgumentException {
		this.setName(name);
		this.setAddress(address);
		this.setMoney(money);
		this.setSalary(salary);
		this.setBank(bank);
		this.deposits = new HashSet<Deposit>();
		this.credits = new HashSet<Credit>();
	}
	
	public Bank getBank() {
		return bank;
	}

	private void setBank(Bank bank) throws InvalidArgumentException {
		if (bank == null) {
			throw new InvalidArgumentException(BANK_NULL_ERROR_MSG);
		}
		
		this.bank = bank;
	}

	public void openDeposit(Deposit deposit) {
		if (this.bank.acceptDeposit(this, deposit)) {
			this.deposits.add(deposit);
		}
	}
	
	public void applyForCredit(Credit credit) {
		if (this.bank.grantCredit(this, credit)) {
			this.credits.add(credit);
		}
	}
	
	public void payCredit(Credit credit, double sum) {
		if (this.credits.contains(credit) && this.money >= sum) {
			this.money -= sum;
			
			try {
				this.bank.setAvaibleMoney(this.bank.getAvaibleMoney() + sum);
			} catch (InvalidArgumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	public double getMonthlyCreditPayments() {
		double monthlyPaymetns = 0.0f;
		
		for (Credit credit : this.credits) {
			monthlyPaymetns += credit.getMonthlyPayment();
		}
		
		return monthlyPaymetns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidArgumentException {
		if (name == null) {
			throw new InvalidArgumentException(NAME_NULL_ERROR_MSG);
		}
		
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) throws InvalidArgumentException {
		if (address == null) {
			throw new InvalidArgumentException(ADDRESS_NULL_ERROR_MSG);
		}
		
		this.address = address;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) throws InvalidArgumentException {
		if (money < 0) {
			throw new InvalidArgumentException(MONEY_ERROR_MSG);
		}
		
		this.money = money;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) throws InvalidArgumentException {
		if (salary < 0) {
			throw new InvalidArgumentException(SALARY_ERROR_MSG);
		}
		
		this.salary = salary;
	}

	@Override
	public String toString() {
		String money = String.format("%.2f", this.money);
		String salary = String.format("%.2f", this.salary);
		
		return name + ", " + address + ", money="
				+ money + ", salary=" + salary + ", bank=" + bank.getName()
				+ ", deposits=" + deposits.size() + ", credits=" + credits.size();
	}
}
