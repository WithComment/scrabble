package entity;

public class WordValidationException extends Exception {
    private Throwable cause;

    public WordValidationException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
