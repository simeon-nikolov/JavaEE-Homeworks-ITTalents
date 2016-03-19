package library;

public abstract class BookLikeObject extends Reading {
	private String author;
	
	public BookLikeObject(String title, String publisher, String author) {
		super(title, publisher);
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((author == null) ? 0 : author.hashCode());
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
		BookLikeObject other = (BookLikeObject) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		return true;
	}
}
