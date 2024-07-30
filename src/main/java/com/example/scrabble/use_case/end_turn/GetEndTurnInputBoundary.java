package com.example.scrabble.use_case.end_turn;
import com.example.scrabble.entity.Game;

import java.io.IOException;

public interface GetEndTurnInputBoundary {


     Game execute(GetEndTurnInputData getEndTurnInputData) throws IOException, ClassNotFoundException;

}
