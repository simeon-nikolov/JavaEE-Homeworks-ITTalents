package courier;

public class Customer extends Person {
private Address address;

public Customer(String fname, String lname, long customerNumber, String tel, Address address) {
	super(fname, lname, customerNumber, tel);
	this.address = address;
}

public Address getAddress() {
	return address;
}


}
