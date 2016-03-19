package smartcourier;


public class LegalEntity extends Person {
	private static final int BULSTAT_DIGITS = 9;
	private static final int BULSTAT_LENGTH = 9;
	
	private String bulstat;

	public LegalEntity(String name, String phoneNumber, Address address) {
		super(name, phoneNumber, address);
		this.bulstat = this.generateBulstat();
	}

	private String generateBulstat() {
		return super.generateDigitsString(BULSTAT_LENGTH, BULSTAT_DIGITS);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bulstat == null) ? 0 : bulstat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		LegalEntity other = (LegalEntity) obj;
		if (bulstat == null) {
			if (other.bulstat != null)
				return false;
		} else if (!bulstat.equals(other.bulstat))
			return false;
		return true;
	}

	
}
