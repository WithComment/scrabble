package com.example.scrabble.use_case.create_game;

import com.example.scrabble.entity.Game;

public interface CreateGameInputBoundary {
  public Game execute(CreateGameInputData data);
}
