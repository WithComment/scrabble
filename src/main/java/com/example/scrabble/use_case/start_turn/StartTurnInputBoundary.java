package com.example.scrabble.use_case.start_turn;

import java.io.IOException;

public interface StartTurnInputBoundary {
    StartTurnOutputData execute(StartTurnInputData data) throws IOException, ClassNotFoundException;
}
