package exceptions;

public class InvalidArgumentException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidArgumentException() {}

	public InvalidArgumentException(String msg) {
		super(msg);
	}

	public InvalidArgumentException(Throwable cause) {
		super(cause);
	}

	public InvalidArgumentException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidArgumentException(String msg, Throwable cause, boolean arg2,
			boolean arg3) {
		super(msg, cause, arg2, arg3);
	}

}
