package exceptions;

public class InvalidArgumentException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidArgumentException() {
	}

	public InvalidArgumentException(String arg0) {
		super(arg0);
	}

	public InvalidArgumentException(Throwable arg0) {
		super(arg0);
	}

	public InvalidArgumentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidArgumentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
