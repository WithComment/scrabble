package entity;

/**
 * Exception thrown when a word validation fails.
 */
public class WordValidationException extends Exception {
    private Throwable cause;

    /**
     * Constructs a new WordValidationException with the specified detail message and cause.
     * @param message The detail message.
     * @param cause The cause of the exception.
     */
    public WordValidationException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * Returns the cause of this exception.
     * @return The cause of this exception.
     */
    @Override
    public Throwable getCause() {
        return cause;
    }
}
