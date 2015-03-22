package nuclibook.models;

/**
 * Model to represent an exception that is thrown when a time of day is invalid.
 */
public class InvalidTimeOfDayException extends Exception {

    /**
     * Constructor to initialise the exception.
     *
     * @param message the error message resulting from the exception.
     */
    public InvalidTimeOfDayException(String message) {
        super(message);
    }

}
