package com.example.scrabble.use_case.contest;

public interface ContestInputBoundary {
    ContestOutputData execute(ContestInputData contestInputData) throws ContestException;
}
