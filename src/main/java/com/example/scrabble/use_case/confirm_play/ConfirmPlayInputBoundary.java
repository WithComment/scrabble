package com.example.scrabble.use_case.confirm_play;

import java.io.IOException;

import com.example.scrabble.entity.Game;

public interface ConfirmPlayInputBoundary {
  Game execute(ConfirmPlayInputData data) throws IOException, ClassNotFoundException;
}
