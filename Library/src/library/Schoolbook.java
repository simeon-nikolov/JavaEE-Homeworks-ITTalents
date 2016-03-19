package library;

public class Schoolbook extends BookLikeObject {
	private static final int SCHOOLBOOL_TAX = 150_000;
	
	private String subject;

	public Schoolbook(String title, String publisher, String author, String subject) {
		super(title, publisher, author);
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	@Override
	public int getTax() {
		return SCHOOLBOOL_TAX;
	}

	@Override
	public int getRentTime() {
		return SCHOOLBOOL_TAX;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		Schoolbook other = (Schoolbook) obj;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	
}
