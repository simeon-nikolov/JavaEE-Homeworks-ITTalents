package smartcourier;

public class Address {
	private City city;
	private String street;
	
	public Address(City city, String street) {
		this.city = city;
		this.street = street;
	}

	public City getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}
}
