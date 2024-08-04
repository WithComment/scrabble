package com.example.scrabble.use_case.contest;

import com.example.scrabble.entity.Game;

public interface ContestInputBoundary {
    Game execute(ContestInputData contestInputData) throws ContestException;
}
