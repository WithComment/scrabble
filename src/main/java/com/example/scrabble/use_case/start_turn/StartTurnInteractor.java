package com.example.scrabble.use_case.start_turn;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.TurnManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StartTurnInteractor implements StartTurnInputBoundary {
    private final GameDataAccess gameDAO;

    @Autowired
    public StartTurnInteractor(GameDataAccess gameDAO) {
      this.gameDAO = gameDAO;
    }

    @Override
    public StartTurnOutputData execute(StartTurnInputData data)throws IOException, ClassNotFoundException {
        Game game = gameDAO.get(data.getGameId());
        TurnManager turnManager = game.getTurnManager();
        turnManager.startTurn();
        return new StartTurnOutputData(turnManager.getCurrentPlayer(), turnManager.getCurrentPlay());
    }
}
