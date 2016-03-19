package smartcourier;

public enum City {
	VELIKO_TARNOVO("Veliko Tarnovo"), SOFIA("Sofia"), RUSE("Ruse"), PLOVDIV("Plovdiv"), VARNA("Varna");
	
	private String name;
	
	private City(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
