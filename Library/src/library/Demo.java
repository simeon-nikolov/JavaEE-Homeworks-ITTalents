package library;

import java.util.Date;

public class Demo {
	private static final int NUMBER_OF_SCHOOLBOOKS = 10;
	private static final int NUMBER_OF_BOOKS = 5;
	private static final int NUMBER_OF_MAGAZINES = 10;

	public static void main(String[] args) {
		Library library = new Library();
		addMagazines(library);
		addBooks(library);
		addSchoolBooks(library);
		
		library.listMagazines();
		library.listBooks();
		library.listSchoolbooks();
		
		new Thread(library).start();
		Thread revisionThread = new Thread(new Revision(library));
		revisionThread.setDaemon(true);
		revisionThread.start();
		
		Book bookToTake = new Book("Harry Potter 1", "Publisher", "Author", new Date(), "Fantasy novel");
		
		new Thread(() -> {
				System.out.println("I'll rent now.");
				library.rentReading(bookToTake);
				try {
					Thread.sleep(500_000);
				} catch (Exception e) {
					return;
				}
				library.returnRentedReading(bookToTake);
		}).start();
		
	}

	private static void addSchoolBooks(Library library) {
		for (int shoolbookNumber = 1; shoolbookNumber <= NUMBER_OF_SCHOOLBOOKS; shoolbookNumber++) {
			Schoolbook schoolbook = new Schoolbook("Calculus " + shoolbookNumber, "Publisher", "Author", "Math");
			library.addReading(schoolbook, Library.SCHOOLBOOKS_TYPE_NAME);
		}
		
		for (int shoolbookNumber = 1; shoolbookNumber <= NUMBER_OF_SCHOOLBOOKS; shoolbookNumber++) {
			Schoolbook schoolbook = new Schoolbook("History book " + shoolbookNumber, "Publisher", "Author", "History");
			library.addReading(schoolbook, Library.SCHOOLBOOKS_TYPE_NAME);
		}
	}

	private static void addBooks(Library library) {
		for (int bookNumber = 1; bookNumber <= NUMBER_OF_BOOKS; bookNumber++) {
			Date date = new Date(100 + bookNumber, 1, 1); 
			Book book = new Book("Harry Potter " + bookNumber, "Publisher", "Author", date, "Fantasy novel");
			library.addReading(book, Library.BOOKS_TYPE_NAME);
		}
	}

	private static void addMagazines(Library library) {
		for (int magazineNumber = 1; magazineNumber <= NUMBER_OF_MAGAZINES; magazineNumber++) {
			Magazine magazine = new Magazine("Play Boy", "Publisher", new Date(), "Erotic", magazineNumber);
			library.addReading(magazine, Library.MAGAZINES_TYPE_NAME);
		}
		
		for (int magazineNumber = 1; magazineNumber <= NUMBER_OF_MAGAZINES; magazineNumber++) {
			Magazine magazine = new Magazine("Cosmopolitan", "Publisher", new Date(), "Fashion", magazineNumber);
			library.addReading(magazine, Library.MAGAZINES_TYPE_NAME);
		}
		
		for (int magazineNumber = 1; magazineNumber <= NUMBER_OF_MAGAZINES; magazineNumber++) {
			Magazine magazine = new Magazine("ICare", "Publisher", new Date(), "Medicine", magazineNumber);
			library.addReading(magazine, Library.MAGAZINES_TYPE_NAME);
		}
	}
}
