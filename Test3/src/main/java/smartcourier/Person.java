package smartcourier;

public class Person {
	private static final int EGN_DIGITS = 9;
	private static final int EGN_LENGTH = 10;
	
	private String name;
	private String phoneNumber;
	private String egn;
	private Address address;
	
	public Person(String name, String phoneNumber, Address address) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.egn = this.generateEgn();
		this.address = address;
	}
	
	private String generateEgn() {
		return this.generateDigitsString(EGN_LENGTH, EGN_DIGITS);
	}
	
	protected String generateDigitsString(int length, int digits) {
		StringBuilder rangomDigitsString = new StringBuilder();
		
		for (int index = 0; index < length; index++) {
			int digit = (int) (Math.random() * (digits + 1));
			rangomDigitsString.append(digit);
		}
		
		return rangomDigitsString.toString();
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEgn() {
		return egn;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((egn == null) ? 0 : egn.hashCode());
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
		Person other = (Person) obj;
		if (egn == null) {
			if (other.egn != null)
				return false;
		} else if (!egn.equals(other.egn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.name + " who lives in " + this.address.getCity();
	}
}
