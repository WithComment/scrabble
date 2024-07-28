package com.example.scrabble.use_case.create_game;

import java.io.IOException;

import com.example.scrabble.entity.Game;

public interface CreateGameInputBoundary {
  public Game execute(CreateGameInputData data) throws IOException, ClassNotFoundException;
}
