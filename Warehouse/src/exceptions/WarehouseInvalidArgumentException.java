package exceptions;

public class WarehouseInvalidArgumentException extends Exception {

	public WarehouseInvalidArgumentException() {
	}

	public WarehouseInvalidArgumentException(String arg0) {
		super(arg0);
	}

	public WarehouseInvalidArgumentException(Throwable arg0) {
		super(arg0);
	}

	public WarehouseInvalidArgumentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public WarehouseInvalidArgumentException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
