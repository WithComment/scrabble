package interface_adapter.end_turn;

import use_case.EndTurn.TurnManagerInteractor;

/**
 * Controller for the EndTurn use case.
 * Manages the interaction between the front-end and the TurnManager.
 */
public class EndTurnController {
    private final TurnManagerInteractor turnManagerInteractor;

    /**
     * Constructs an EndTurnController with a specified TurnManager.
     *
     * @param turnManagerInteractor the TurnManager managing the game turns
     */
    public EndTurnController(TurnManagerInteractor turnManagerInteractor) {
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

    public void execute(boolean contestSucceed) {
        turnManagerInteractor.dealContest(contestSucceed);
        turnManagerInteractor.endTurn();
        turnManagerInteractor.startTurn();
    }
}
