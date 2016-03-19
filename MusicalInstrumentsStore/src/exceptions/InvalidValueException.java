package exceptions;

public class InvalidValueException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidValueException() {
	}

	public InvalidValueException(String arg0) {
		super(arg0);
	}

	public InvalidValueException(Throwable arg0) {
		super(arg0);
	}

	public InvalidValueException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidValueException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
