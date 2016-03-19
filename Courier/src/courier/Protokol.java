package courier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class Protokol {
	private Person from;
	private Person to;
//	private ArrayList<Integer> mailItemsList;
	
	private LocalDateTime issueTime;

	public Protokol(Person from, Person to, LocalDateTime issueTime) {
		this.from = from;
		this.to = to;
//		this.mailItemsList = new ArrayList<Integer>();
		this.issueTime = issueTime;
		
		
	}

	public Person getFrom() {
		return from;
	}

	public Person getTo() {
		return to;
	}

	public LocalDateTime getIssueTime() {
		return issueTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((issueTime == null) ? 0 : issueTime.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		Protokol other = (Protokol) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (issueTime == null) {
			if (other.issueTime != null)
				return false;
		} else if (!issueTime.equals(other.issueTime))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override NE MI RABOTI DURVOTO MINAVAM NA HASHMAP
//	public int compareTo(Protokol p) {
//		if (this.getFrom().equals(p.getFrom()) && this.getTo().equals(p.getTo())
//				&& this.getIssueTime().equals(p.getIssueTime())) {
//			return 0;
//		}
////		if(this.getFrom().getCustomerNumber() == p.getFrom().getCustomerNumber()){
////			return (int) (this.getFrom().getCustomerNumber()-p.getFrom().getCustomerNumber());
////		}else{return (int) (this.getFrom().getCustomerNumber()-p.getFrom().getCustomerNumber());}
//		return -1;
//	}

}
