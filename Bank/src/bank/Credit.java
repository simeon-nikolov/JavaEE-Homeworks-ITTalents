package bank;

import exceptions.InvalidArgumentException;

public class Credit extends BankProduct {
	private static final String MONTHLY_PAYMENT_ERROR_MSG = "Monthly payment can't be negative value!";
	
	private double monthlyPayment;
	private double initialCreditLoan;
	
	public Credit(String accountName, int yearInterestPercent, int period,
			double moneyAmount) throws InvalidArgumentException {
		super(accountName, yearInterestPercent, period, moneyAmount);
		this.initialCreditLoan = this.getMoneyAmount();
		this.setMonthlyPayment(((yearInterestPercent / 100.0) * moneyAmount) / MONTHS_IN_THE_YEAR);
	}

	public double getInitialCreditLoan() {
		return initialCreditLoan;
	}

	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(double monthlyPayment) throws InvalidArgumentException {
		if (monthlyPayment < 0) {
			throw new InvalidArgumentException(MONTHLY_PAYMENT_ERROR_MSG);
		}
		
		this.monthlyPayment = monthlyPayment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(initialCreditLoan);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthlyPayment);
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
		Credit other = (Credit) obj;
		if (Double.doubleToLongBits(initialCreditLoan) != Double
				.doubleToLongBits(other.initialCreditLoan))
			return false;
		if (Double.doubleToLongBits(monthlyPayment) != Double
				.doubleToLongBits(other.monthlyPayment))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String money = String.format("%.2f", this.getMoneyAmount());
		String loan = String.format("%.2f", initialCreditLoan);
		
		return "Credit [Monthly Payment=" + monthlyPayment
				+ ", Credit Loan=" + loan
				+ ", Account Name=" + getAccountName()
				+ ", Year Interest Percent=" + getYearInterestPercent()
				+ ", Period=" + getPeriod() + ", Money Amount="
				+ money + "]";
	}
}
