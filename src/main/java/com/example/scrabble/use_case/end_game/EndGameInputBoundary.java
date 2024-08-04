package com.example.scrabble.use_case.end_game;

public interface EndGameInputBoundary {
    EndGameOutputData execute(EndGameInputData endGameInputData) throws RuntimeException;
}
