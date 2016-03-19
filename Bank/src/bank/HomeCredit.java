package bank;

import exceptions.InvalidArgumentException;

public class HomeCredit extends Credit {
	private static final int INTEREST = 6;

	public HomeCredit(String accountName, int period, double moneyAmount)
			throws InvalidArgumentException {
		super(accountName, INTEREST, period, moneyAmount);

	}

}
