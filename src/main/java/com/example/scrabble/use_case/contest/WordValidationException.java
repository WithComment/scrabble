package com.example.scrabble.use_case.contest;

/**
 * Exception thrown when a word validation fails.
 */
public class WordValidationException extends RuntimeException {
    private final Throwable cause;

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
