package library;

import java.util.Date;
import java.util.LinkedList;

public class History {
	private LinkedList<Rent> history;
	
	public History() {
		this.history = new LinkedList<Rent>();
	}
	
	public class Rent {		
		private Date dateTaken;
		private Date dueDate;
		private Date dateOfReturn;
		private int tax;
		private int interest;
		
		public Rent(int tax, int rentTime) {
			this.dateTaken = new Date()	;
			this.tax = tax;
			this.dueDate = new Date(this.dateTaken.getTime() + rentTime);
		}
		
		public Date getDateTaken() {
			return dateTaken;
		}
		
		public void setDateTaken(Date dateTaken) {
			this.dateTaken = dateTaken;
		}
		
		public Date getDateOfReturn() {
			return dateOfReturn;
		}
		
		public void setDateOfReturn(Date dateOfReturn) {
			this.dateOfReturn = dateOfReturn;
		}

		public int getInterest() {
			return interest;
		}

		public void setInterest(int interest) {
			this.interest = interest;
		}

		public int getTax() {
			return tax;
		}
		
		public Date getDueDate() {
			return dueDate;
		}

	}
	
	public void addRentToHistory(Rent rent) {
		if (rent != null) {
			this.history.add(rent);
		}
	}
	
	public Rent getLastRent() {
		return this.history.getLast();
	}
	
	public int getSize() {
		return this.history.size();
	}
}
