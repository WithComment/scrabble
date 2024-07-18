package entity;

public interface WordValidator {
    boolean wordIsValid(String word) throws WordValidationException;
}
