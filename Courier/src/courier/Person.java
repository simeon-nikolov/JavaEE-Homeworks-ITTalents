package courier;

import java.util.TreeMap;

public abstract class Person {
	private String fname;
	private String lname;
	private long customerNumber;
	private String tel;
	private static TreeMap<Long, Person> customerList = new TreeMap<Long, Person>();
	
		
	public Person(String fname, String lname, long customerNumber, String tel) {
		this.fname = fname;
		this.lname = lname;
		this.customerNumber = customerNumber;
		this.tel = tel;
		Person.customerList.put(customerNumber, this);
	}
	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}
	public long getCustomerNumber() {
		return customerNumber;
	}
	public String getTel() {
		return tel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (customerNumber ^ (customerNumber >>> 32));
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
		if (customerNumber != other.customerNumber)
			return false;
		return true;
	}
	
	
	
}
