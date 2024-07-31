package com.example.scrabble.use_case.place_letter;

import com.example.scrabble.entity.Game;

public interface PlaceLetterInputBoundary {
  Game execute(PlaceLetterInputData data);
}