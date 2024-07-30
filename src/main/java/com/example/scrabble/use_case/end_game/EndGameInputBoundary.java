package com.example.scrabble.use_case.end_game;

import com.example.scrabble.entity.Game;

import java.io.IOException;

public interface EndGameInputBoundary {
    Game execute(EndGameInputData endGameInputData) throws IOException, ClassNotFoundException, RuntimeException;
}
