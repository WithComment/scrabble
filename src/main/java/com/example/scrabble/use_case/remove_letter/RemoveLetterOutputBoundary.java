package com.example.scrabble.use_case.remove_letter;

public interface RemoveLetterOutputBoundary {
    public void prepareSuccessView(RemoveLetterOutputData removeLetterOutputData);
    public void prepareFailureView(RemoveLetterOutputData removeLetterOutputData);
}
