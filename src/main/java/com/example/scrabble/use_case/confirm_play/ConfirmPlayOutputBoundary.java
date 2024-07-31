package com.example.scrabble.use_case.confirm_play;

public interface ConfirmPlayOutputBoundary {
  void prepareFailView(String msg);
  void prepareSuccessView(ConfirmPlayOutputData data);
}
