package postoffice;

public abstract class Person {
	private String firstName;
	private String lastName;
	private PostOffice postOffice;
	
	public Person(String firstName, String lastName, PostOffice postOffice) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.postOffice = postOffice;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public PostOffice getPostOffice() {
		return postOffice;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
