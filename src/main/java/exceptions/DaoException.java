package exceptions;

public class DaoException extends AppException {

	private static final long serialVersionUID = 1L;


	public DaoException(String message) {
		super(message);
	}


	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
