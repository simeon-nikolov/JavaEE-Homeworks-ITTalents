package postoffice;

public class Package extends PostObject {
	private static final int PACKAGE_TIME = 1500;
	private static final double LARGE_OR_BREAKABLE_PACKAGE_TAX_COEFFICIENT = 1.5;
	private static final int PACKAGE_TAX = 200;
	
	private int length;
	private int width;
	private int height;
	private boolean isBreakable;

	public Package(Citizen sender, Citizen receiver, int length, int width, int height, boolean isBreakable) {
		super(sender, receiver);
		this.length = length;
		this.width = width;
		this.height = height;
		this.isBreakable = isBreakable;
		
	}

	@Override
	public int getTax() {
		int tax = PACKAGE_TAX;
		
		if (this.length > 60 || this.width > 60 || this.height > 60) {
			tax *= LARGE_OR_BREAKABLE_PACKAGE_TAX_COEFFICIENT;
		}
		
		if (this.isBreakable) {
			tax *= LARGE_OR_BREAKABLE_PACKAGE_TAX_COEFFICIENT;
		}
		
		return tax;
	}

	@Override
	public int getTimeToSend() {
		return PACKAGE_TIME;
	}

	
	
	public boolean isBreakable() {
		return this.isBreakable;
	}

	@Override
	public String toString() {
		return "Package " + super.toString() + 
				" length=" + length + ", width=" + width + ", height=" + height + 
				(isBreakable ? ", breakable" : "");
	}

	
}
