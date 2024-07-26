package interface_adapter.end_turn;

import entity.Game;
import entity.Player;
import use_case.EndTurn.EndTurnInteractor;
import use_case.EndTurn.GetEndTurnInputData;

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

    public void execute(boolean contestSucceed, Game game, List<List> wordsToBeConfirmed) {
        ArrayList<Player> players = new ArrayList<>();
        GetEndTurnInputData getEndTurnInputData = new GetEndTurnInputData(players, false, false,
                game.getLetterBag(), game,wordsToBeConfirmed);
        turnManagerInteractor.execute(getEndTurnInputData);
    }
}
