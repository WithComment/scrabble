package com.example.scrabble.use_case.contest;

public class ContestException extends RuntimeException {
    private Exception cause;

    public ContestException(String message) {
        super(message);
    }

    public ContestException(String message, Exception cause) {
        super(message);
        this.cause = cause;
    }

    public Exception getCause() {
        return cause;
    }
}
