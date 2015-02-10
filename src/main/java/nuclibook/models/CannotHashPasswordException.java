package nuclibook.models;

public class CannotHashPasswordException extends Exception {

	public CannotHashPasswordException(String message) {
		super(message);
	}

}
