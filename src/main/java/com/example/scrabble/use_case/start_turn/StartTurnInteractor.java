package com.example.scrabble.use_case.start_turn;

import com.example.scrabble.entity.TurnManager;

public class StartTurnInteractor implements StartTurnInputBoundary {
    private final StartTurnOutputBoundary outputBoundary;

    public StartTurnInteractor(StartTurnOutputBoundary outputBoundary) {
      this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(StartTurnInputData data) {
      TurnManager turnManager = data.getTurnManager();
      turnManager.startTurn();
      outputBoundary.prepareView(new StartTurnOutputData(turnManager.getCurrentPlayer(), turnManager.getCurrentPlay()));
    }
}