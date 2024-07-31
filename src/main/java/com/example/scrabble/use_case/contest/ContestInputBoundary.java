package com.example.scrabble.use_case.contest;

import com.example.scrabble.entity.Game;

import java.io.IOException;

public interface ContestInputBoundary {
    Game execute(ContestInputData contestInputData) throws IOException, ClassNotFoundException, ContestException;
}
