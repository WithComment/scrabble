package com.example.scrabble.use_case.start_turn;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Implements the use case interactor for ending the turn.
 * This class is responsible for processing the end turn actions,
 * handling contests, updating the board, and managing player states.
 */
@Service
public class StartTurnInteractor implements StartTurnInputBoundary {
    private final GameDataAccess gameDataAccess;

    /**
     * Constructs an EndTurnInteractor with a specified presenter and game data access.
     * @param gameDataAccess The data access object for retrieving and storing game state.
     */
    @Autowired
    public StartTurnInteractor( GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    @Override
    public StartTurnOutputData execute(StartTurnInputData startTurnInputData) {
        Game game = gameDataAccess.get(startTurnInputData.getGameId());

        game.endTurn();
        game.startTurn();


        gameDataAccess.update(game);
        return new StartTurnOutputData(game.getId());
    }
}
