package com.example.scrabble.use_case.place_letter;

import java.io.IOException;

import com.example.scrabble.entity.Game;

public interface PlaceLetterInputBoundary {
  Game execute(PlaceLetterInputData data) throws IOException, ClassNotFoundException;
}