package com.example.scrabble.use_case.start_turn;

import com.example.scrabble.entity.Game;

import java.io.IOException;

public interface StartTurnInputBoundary {
    Game execute(StartTurnInputData data) throws IOException, ClassNotFoundException;
}
