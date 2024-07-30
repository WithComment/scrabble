package com.example.scrabble.interface_adapter.end_turn;

import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import com.example.scrabble.use_case.end_turn.GetEndTurnInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the EndTurn use case.
 * Manages the interaction between the front-end and the TurnManager.
 */
public class EndTurnController {
    private final EndTurnInteractor turnManagerInteractor;

    /**
     * Constructs an EndTurnController with a specified TurnManager.
     *
     * @param turnManagerInteractor the TurnManager managing the game turns
     */
    public EndTurnController(EndTurnInteractor turnManagerInteractor) {
        this.turnManagerInteractor = turnManagerInteractor;
    }

    /*
     * Ends the current player's turn.
     * Updates the TurnManager and starts the next player's turn.
     */

    /**
     * Handles the result of a contest.
     * Updates the TurnManager based on the contest result.
     *
     * @param contestSucceed a boolean indicating whether the contest succeeded
     */

    public void execute(boolean contestSucceed, int gameId, List<List<Integer>> wordsToBeConfirmed) throws IOException, ClassNotFoundException {
        ArrayList<Player> players = new ArrayList<>();
        GetEndTurnInputData getEndTurnInputData = new GetEndTurnInputData(gameId, false, false ,wordsToBeConfirmed);
        turnManagerInteractor.execute(getEndTurnInputData);
    }
}
