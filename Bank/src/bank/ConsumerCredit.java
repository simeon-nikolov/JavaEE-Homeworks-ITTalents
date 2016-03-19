package bank;

import exceptions.InvalidArgumentException;

public class ConsumerCredit extends Credit {
	private static final int INTEREST = 10;

	public ConsumerCredit(String accountName, int period, double moneyAmount)
			throws InvalidArgumentException {
		super(accountName, INTEREST, period, moneyAmount);

	}

}
