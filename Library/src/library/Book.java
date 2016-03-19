package library;

import java.util.Date;

public class Book extends BookLikeObject implements IDatePublished {
	private static final int BOOK_TAX = 200;
	private static final int TIME_FOR_RENTING = 300_000;
	
	private Date dateOfPublishing;
	private String genre;

	public Book(String title, String publisher, String author, Date dateOfPublishing, String genre) {
		super(title, publisher, author);
		this.dateOfPublishing = dateOfPublishing;
		this.genre = genre;
	}

	public Date getDateOfPublishing() {
		return dateOfPublishing;
	}

	public String getGenre() {
		return genre;
	}

	@Override
	public int getTax() {
		return BOOK_TAX;
	}

	@Override
	public int getRentTime() {
		return TIME_FOR_RENTING;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
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
		Book other = (Book) obj;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		return true;
	}
}
