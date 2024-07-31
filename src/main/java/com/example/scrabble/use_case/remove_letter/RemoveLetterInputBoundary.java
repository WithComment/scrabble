package com.example.scrabble.use_case.remove_letter;

import com.example.scrabble.entity.Game;

public interface RemoveLetterInputBoundary {
    Game execute(RemoveLetterInputData removeLetterInputData);
}
