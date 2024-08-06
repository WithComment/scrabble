package com.example.scrabble.use_case.join_game;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Implements the use case interactor for ending the turn.
 * This class is responsible for processing the end turn actions,
 * handling contests, updating the board, and managing player states.
 */
@Service
public class JoinGameInteractor implements JoinGameInputBoundary {
    private final GameDataAccess gameDataAccess;

    /**
     * Constructs an EndTurnInteractor with a specified presenter and game data access.
     * @param gameDataAccess The data access object for retrieving and storing game state.
     */
    @Autowired
    public JoinGameInteractor( GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    @Override
    public JoinGameOutputData execute(JoinGameInputData joinGameInputData) {
        try {
            Game game = gameDataAccess.get(joinGameInputData.getGameId());
        } catch (Exception e){
            throw new IllegalArgumentException("Game with the specified ID does not exist.");
        }

        Game game = gameDataAccess.get(joinGameInputData.getGameId());
        game.addPlayer(joinGameInputData.getPlayerName());

        gameDataAccess.update(game);
        return new JoinGameOutputData(joinGameInputData.getPlayerName());
    }
}
