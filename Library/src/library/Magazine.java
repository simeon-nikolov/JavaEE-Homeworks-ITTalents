package library;

import java.util.Date;

public class Magazine extends Reading implements IDatePublished {
	private Date dateOfPublishing;
	private String category;
	private int issueNumber;

	public Magazine(String title, String publisher, Date dateOfPublishing, String category, int issueNumber) {
		super(title, publisher);
		this.dateOfPublishing = dateOfPublishing;
		this.category = category;
		this.issueNumber = issueNumber;
	}

	public Date getDateOfPublishing() {
		return dateOfPublishing;
	}

	public String getCategory() {
		return category;
	}

	public int getIssueNumber() {
		return issueNumber;
	}

	@Override
	public int getTax() {
		return 0;
	}

	@Override
	public int getRentTime() {
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + issueNumber;
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
		Magazine other = (Magazine) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (issueNumber != other.issueNumber)
			return false;
		return true;
	}
}
