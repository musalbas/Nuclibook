package nuclibook.models;

/**
 * Model to represent an exception that is thrown when a password cannot be hashed.
 */
public class CannotHashPasswordException extends Exception {

    /**
     * Constructor to initialise the exception.
     *
     * @param message the error message resulting from the exception.
     */
    public CannotHashPasswordException(String message) {
        super(message);
    }

}
