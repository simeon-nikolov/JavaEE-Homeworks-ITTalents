package bank;

import exceptions.InvalidArgumentException;

public class LongDeposit extends Deposit {
	private static final int PERIOD = 12;
	private static final int INTEREST = 5;

	public LongDeposit(String accountName, double moneyAmount) throws InvalidArgumentException {
		super(accountName, INTEREST, PERIOD, moneyAmount);
		
	}


}
