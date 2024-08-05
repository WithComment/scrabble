package com.example.scrabble.use_case.end_turn;

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
public class EndTurnInteractor implements GetEndTurnInputBoundary {
    private final GameDataAccess gameDataAccess;

    /**
     * Constructs an EndTurnInteractor with a specified presenter and game data access.
     * @param gameDataAccess The data access object for retrieving and storing game state.
     */
    @Autowired
    public EndTurnInteractor( GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    @Override
    public EndTurnOutputData execute(GetEndTurnInputData getEndTurnInputData) {
        Game game = gameDataAccess.get(getEndTurnInputData.getGameId());
        List<Move> Moves;

        if (game.isEndTurn()) {
            Board currentBoard = game.getBoard();
            Play play = game.getLastPlay();
            Moves = play.getMoves();
            for (Move move : Moves) {
                int x = move.getX();
                int y = move.getY();
                currentBoard.confirm(x, y);
            }

            Player currentPlayer = game.getCurrentPlayer();

            int toDraw = 7 - currentPlayer.getInventory().size();
            currentPlayer.addLetter(game.getLetterBag().drawLetters(toDraw));
        }


        gameDataAccess.update(game);
        return new EndTurnOutputData(game.getId());
    }
}

