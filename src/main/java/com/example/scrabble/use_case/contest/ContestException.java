package com.example.scrabble.use_case.contest;

public class ContestException extends Exception{
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
