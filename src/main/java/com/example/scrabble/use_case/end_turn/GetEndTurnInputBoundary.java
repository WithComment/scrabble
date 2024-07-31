package com.example.scrabble.use_case.end_turn;
import com.example.scrabble.entity.Game;

public interface GetEndTurnInputBoundary {

     Game execute(GetEndTurnInputData getEndTurnInputData);

}
