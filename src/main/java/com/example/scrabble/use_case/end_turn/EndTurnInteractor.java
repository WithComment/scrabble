package com.example.scrabble.use_case.end_turn;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Move;
import com.example.scrabble.entity.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Implements the use case interactor for ending the turn.
 * This class is responsible for processing the end turn actions,
 * handling contests, updating the board, and managing player states.
 */
@Service
public class EndTurnInteractor implements EndTurnInputBoundary {
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
    public EndTurnOutputData execute(EndTurnInputData getEndTurnInputData) {
        int gameID = getEndTurnInputData.getGameId();
        Game game = gameDataAccess.get(gameID);
        List<Move> moves = game.getCurrentPlay().getMoves();

        Board currentBoard = game.getBoard();
        for (Move move : moves) {
            currentBoard.confirm(move.getX(), move.getY());
        }

        Player currentPlayer = game.getCurrentPlayer();

        int toDraw = 7 - currentPlayer.getInventory().size();
        currentPlayer.addLetter(game.getLetterBag().drawLetters(toDraw));
        System.out.println(currentPlayer.getTempScore());
        currentPlayer.confirmTempScore();
        game.endTurn();
        game.startTurn();

        gameDataAccess.update(game);
        return new EndTurnOutputData(gameID);
    }
}

