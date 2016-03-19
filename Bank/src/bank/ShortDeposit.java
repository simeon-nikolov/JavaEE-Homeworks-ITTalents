package bank;

import exceptions.InvalidArgumentException;

public class ShortDeposit extends Deposit {
	private static final int PERIOD = 3;
	private static final int INTEREST = 3;

	public ShortDeposit(String accountName, double moneyAmount) throws InvalidArgumentException {
		super(accountName, INTEREST, PERIOD, moneyAmount);
		
	}

}
