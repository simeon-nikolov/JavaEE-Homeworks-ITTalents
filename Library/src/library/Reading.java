package library;

public abstract class Reading implements Readable {
	private String title;
	private String publisher;
	private History history;
	
	public Reading(String title, String publisher) {
		this.title = title;
		this.publisher = publisher;
		this.history = new History();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getPublisher() {
		return publisher;
	}

	@Override
	public History getHistory() {
		return history;
	}
	
	public abstract int getTax();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Reading other = (Reading) obj;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
