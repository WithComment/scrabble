package com.example.scrabble.use_case.end_game;

import com.example.scrabble.entity.Game;

public interface EndGameInputBoundary {
    Game execute(EndGameInputData endGameInputData) throws RuntimeException;
}
