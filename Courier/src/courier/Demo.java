package courier;

public class Demo implements IConstants{

	public static void main(String[] args) {
		Address varna = new Address(VARNA, "Mushmorok 1");
		Address sofia = new Address(SOFIA, "Lisica 5");
		Address ruse = new Address(RUSE, "Kaka Svraka 5");
		Address plovdiv = new Address(PLOVDIV, "Pod tepeto 8");
		Customer varnenec = new Customer("Ivan", "OtVarna", 8711110912L, "0888123123", varna);
		Customer sofianec = new Customer("Petkan", "OtSofia", 8711110912L, "0888123123", sofia);
		Customer maina = new Customer("Dragan", "OtPlovdiv", 8711110912L, "0888123123", plovdiv);
		Customer rusi = new Customer("Mincho", "OtRuse", 8711110912L, "0888123123", ruse);
		MailItem itemOne = new MailItem(varnenec, sofianec, "Slivi");
		MailItem itemTwo = new MailItem(varnenec, sofianec, "Slivi");
		MailItem itemThree = new MailItem(varnenec, sofianec, "Slivi");
		MailItem itemFour = new MailItem(varnenec, sofianec, "Slivi");
		MailItem itemFive = new MailItem(varnenec, maina, "Slivi");
		MailItem itemSix = new MailItem(varnenec, maina, "Slivi");
		MailItem itemSeven = new MailItem(varnenec, rusi, "Slivi");
		MailItem itemEight = new MailItem(varnenec, rusi, "Slivi");
		MailItem itemNine = new MailItem(varnenec, rusi, "Slivi");
		MailItem itemTen = new MailItem(sofianec, rusi, "Slivi");
		MailItem itemEleven = new MailItem(sofianec, maina, "Slivi");
		MailItem itemTwelfe = new MailItem(sofianec, maina, "Slivi");
		MailItem itemThirtteen = new MailItem(sofianec, varnenec, "Slivi");
		MailItem item14 = new MailItem(sofianec, varnenec, "Slivi");
		CentralWarehouse cw = new CentralWarehouse(TURNOVO);
		Office varnaOffice = new Office(VARNA, cw);
		Office plovdivOffice = new Office(PLOVDIV, cw);
		Office sofiaOffice = new Office(SOFIA, cw);
		Office ruseOffice = new Office(RUSE, cw);
		
		LocalEmployee varnaEmpl = new LocalEmployee("Kiro", "Peshev", 8712345678L, "3522525254", varnaOffice);
		LocalEmployee plovdivEmpl = new LocalEmployee("Kiro", "Peshev", 8712345678L, "3522525254", plovdivOffice);
		LocalEmployee sofiaEmpl = new LocalEmployee("Kiro", "Peshev", 8712345678L, "3522525254", sofiaOffice);
		LocalEmployee ruseEmpl = new LocalEmployee("Kiro", "Peshev", 8712345678L, "3522525254", ruseOffice);
		Driver sofTurn = new Driver("Kiro", "Peshev", 8712345678L, "3522525254", sofiaOffice, cw);
		Driver rusTurn = new Driver("Kiro", "Peshev", 8712345678L, "3522525254", ruseOffice, cw);
		Driver varnaTurn = new Driver("Kiro", "Peshev", 8712345678L, "3522525254", varnaOffice, cw);
		Driver plovdTurn = new Driver("Kiro", "Peshev", 8712345678L, "3522525254", plovdivOffice, cw);
		Driver turnsof = new Driver("Kiro", "Peshev", 8712345678L, "3522525254", cw, sofiaOffice);
		Driver turnPlovd = new Driver("Kiro", "Peshev", 8712345678L, "3522525254", cw, plovdivOffice);
		Driver turnRuse = new Driver("Kiro", "Peshev", 8712345678L, "3522525254", cw, ruseOffice);
		Driver turnVarna = new Driver("Kiro", "Peshev", 8712345678L, "3522525254", cw, varnaOffice);
		new Thread(varnaEmpl).start();
		new Thread(plovdivEmpl).start();
		new Thread(sofiaEmpl).start();
		new Thread(ruseEmpl).start();
		new Thread(sofTurn).start();
		new Thread(rusTurn).start();
		new Thread(varnaTurn).start();
		new Thread(plovdTurn).start();
		new Thread(turnsof).start();
		new Thread(turnPlovd).start();
		new Thread(turnRuse).start();
		new Thread(turnVarna).start();
		varnaEmpl.acceptFromCustomer(itemOne);
		varnaEmpl.acceptFromCustomer(itemTwo);
		varnaEmpl.acceptFromCustomer(itemThree);
		varnaEmpl.acceptFromCustomer(itemFour);
		varnaEmpl.acceptFromCustomer(itemFive);
		varnaEmpl.acceptFromCustomer(itemSix);
		varnaEmpl.acceptFromCustomer(itemSeven);
		varnaEmpl.acceptFromCustomer(itemEight);
		varnaEmpl.acceptFromCustomer(itemNine);
		sofiaEmpl.acceptFromCustomer(itemTen);
		sofiaEmpl.acceptFromCustomer(itemEleven);
		sofiaEmpl.acceptFromCustomer(itemTwelfe);
		sofiaEmpl.acceptFromCustomer(itemThirtteen);
		sofiaEmpl.acceptFromCustomer(item14);
		
		
	}

}
