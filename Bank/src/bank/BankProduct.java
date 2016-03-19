package bank;

import exceptions.InvalidArgumentException;

public abstract class BankProduct {
	protected static final double MONTHS_IN_THE_YEAR = 12.0;
	protected static final int MIN_MONTHS_PERIOD = 1;
	protected static final int MAX_MONTHS_PERIOD = 60;
	
	private String accountName;
	private int yearInterestPercent;
	private int period;
	private double moneyAmount;
	
	public BankProduct(String accountName, int yearInterestPercent, int period,
			double moneyAmount) throws InvalidArgumentException {
		this.setAccountName(accountName);
		this.setYearInterestPercent(yearInterestPercent);
		this.setPeriod(period);
		this.setMoneyAmount(moneyAmount);
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) throws InvalidArgumentException {
		if (accountName == null) {
			throw new InvalidArgumentException("Invalid name! Account name can't be null!");
		}
		
		this.accountName = accountName;
	}

	public int getYearInterestPercent() {
		return yearInterestPercent;
	}

	public void setYearInterestPercent(int yearInterestPercent) throws InvalidArgumentException {
		if (yearInterestPercent < 0) {
			throw new InvalidArgumentException("Year interest percent can't be negative value!");
		}
		
		this.yearInterestPercent = yearInterestPercent;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) throws InvalidArgumentException {
		if (period < MIN_MONTHS_PERIOD || period > MAX_MONTHS_PERIOD) {
			throw new InvalidArgumentException("Period must be between 1 and 60 months!");
		}
		
		this.period = period;
	}

	public double getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(double moneyAmount) throws InvalidArgumentException {
		this.moneyAmount = moneyAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountName == null) ? 0 : accountName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(moneyAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + period;
		result = prime * result + yearInterestPercent;
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
		BankProduct other = (BankProduct) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (Double.doubleToLongBits(moneyAmount) != Double
				.doubleToLongBits(other.moneyAmount))
			return false;
		if (period != other.period)
			return false;
		if (yearInterestPercent != other.yearInterestPercent)
			return false;
		return true;
	}
}
