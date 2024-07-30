package com.example.scrabble.interface_adapter.end_turn;

import com.example.scrabble.use_case.end_turn.GetEndTurnOutputData;
//import interface_adapter.GameViewModel;
import com.example.scrabble.use_case.end_turn.GetEndTurnOutputBoundary;

public class EndTurnPresenter implements GetEndTurnOutputBoundary {
//    private GameViewModel gameViewModel;
//
    public EndTurnPresenter() {
    }
//
//    public GameViewModel getGameViewModel() {
//        return gameViewModel;
//    }

    @Override
    public void prepareSuccessView(GetEndTurnOutputData data) {
    }

    @Override
    public void prepareFailView(String error) {
    }

    @Override
    public void prepareView(GetEndTurnOutputData outputData) {

    }
}

