package com.example.scrabble.use_case.end_turn;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.TurnManager;

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
    public Game execute(GetEndTurnInputData getEndTurnInputData) {
        Game game = gameDataAccess.get(getEndTurnInputData.getGameId());
        TurnManager turnManager = game.getTurnManager();

        for (Player player : game.getPlayers()) {
            turnManager.addPlayer(player);
        }

        if (turnManager.isEndTurn()) {
            Board currentBoard = game.getBoard();
            for (List<Integer> wordToBeConfirmed : getEndTurnInputData.getWordsToBeConfirmed()) {
                int x = wordToBeConfirmed.get(0);
                int y = wordToBeConfirmed.get(1);
                currentBoard.confirm(x, y);
            }

            Player currentPlayer = turnManager.getCurrentPlayer();
            if (getEndTurnInputData.isContestSucceed()) {
                currentPlayer.eraseTempScore();
            } else {
                currentPlayer.confirmTempScore();
            }
            int toDraw = 7 - currentPlayer.getInventory().size();
            currentPlayer.addLetter(game.getLetterBag().drawLetters(toDraw));
            turnManager.endTurn();
            turnManager.startTurn();
        }

        if (getEndTurnInputData.isContest()) {
            turnManager.dealContest(getEndTurnInputData.isContestSucceed());
        }

        gameDataAccess.update(game);
        return game;
    }
}

