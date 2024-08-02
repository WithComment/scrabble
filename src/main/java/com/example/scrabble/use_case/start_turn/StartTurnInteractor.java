package com.example.scrabble.use_case.start_turn;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
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
        game.startTurn();
        return new StartTurnOutputData(game.getCurrentPlayer(), game.getCurrentPlay());
    }
}
