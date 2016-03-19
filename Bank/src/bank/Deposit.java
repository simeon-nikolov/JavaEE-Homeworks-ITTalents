package bank;

import exceptions.InvalidArgumentException;

public class Deposit extends BankProduct {
	private static final String MONEY_AMOUNT_ERROR_MSG = "Money amount for deposit can't be negative value!";
	private static final String MONTHLY_PAID_SUM_ERROR_MSG = "Monthly paid sum can't be negative value!";
	
	private double monthlyPaidSum;

	public Deposit(String accountName, int yearInterestPercent, int period,
			double moneyAmount) throws InvalidArgumentException {
		super(accountName, yearInterestPercent, period, moneyAmount);
		this.setMonthlyPaidSum(((yearInterestPercent / 100.0) * moneyAmount) / MONTHS_IN_THE_YEAR);
	}
	
	public double getMonthlyPaidSum() {
		return monthlyPaidSum;
	}

	public void setMonthlyPaidSum(double monthlyPaidSum) throws InvalidArgumentException {
		if (monthlyPaidSum < 0) {
			throw new InvalidArgumentException(MONTHLY_PAID_SUM_ERROR_MSG);
		}
		
		this.monthlyPaidSum = monthlyPaidSum;
	}
	
	@Override
	public void setMoneyAmount(double moneyAmount) throws InvalidArgumentException {
		if (moneyAmount < 0) {
			throw new InvalidArgumentException(MONEY_AMOUNT_ERROR_MSG);
		}
		
		super.setMoneyAmount(moneyAmount);
	}
	
	public double calculateDepositInterest() {
		double depositInterest = this.getMoneyAmount() * (this.getYearInterestPercent() / 100.0);
		return depositInterest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(monthlyPaidSum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deposit other = (Deposit) obj;
		if (Double.doubleToLongBits(monthlyPaidSum) != Double
				.doubleToLongBits(other.monthlyPaidSum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String money = String.format("%.2f", this.getMoneyAmount());
		String monthlyPaidSum = String.format("%.2f", this.getMonthlyPaidSum());
		
		return "Deposit [Monthly Paid Sum=" + monthlyPaidSum
				+ ", Account Name=" + getAccountName()
				+ ", Year Interest Percent=" + getYearInterestPercent()
				+ ", Period=" + getPeriod() + ", Money Amount="
				+ money + "]";
	}
}
