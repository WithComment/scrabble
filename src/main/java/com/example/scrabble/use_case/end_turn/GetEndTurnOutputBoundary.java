package com.example.scrabble.use_case.end_turn;


public interface GetEndTurnOutputBoundary {
    void prepareSuccessView(GetEndTurnOutputData data);

    void prepareFailView(String error);

    void prepareView(GetEndTurnOutputData outputData);
}
