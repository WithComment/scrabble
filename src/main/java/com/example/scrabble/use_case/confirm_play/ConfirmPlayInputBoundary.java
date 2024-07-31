package com.example.scrabble.use_case.confirm_play;

import com.example.scrabble.entity.Game;

public interface ConfirmPlayInputBoundary {
  Game execute(ConfirmPlayInputData data);
}