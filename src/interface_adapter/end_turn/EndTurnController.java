package interface_adapter.end_turn;

import use_case.EndTurn.TurnManager;

/**
 * Controller for the EndTurn use case.
 * Manages the interaction between the front-end and the TurnManager.
 */
public class EndTurnController {
    private final TurnManager turnManager;

    /**
     * Constructs an EndTurnController with a specified TurnManager.
     *
     * @param turnManager the TurnManager managing the game turns
     */
    public EndTurnController(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    /**
     * Ends the current player's turn.
     * Updates the TurnManager and starts the next player's turn.
     */
    public void endTurn() {
        turnManager.endTurn();
        turnManager.startTurn();
    }

    /**
     * Handles the result of a contest.
     * Updates the TurnManager based on the contest result.
     *
     * @param contestSucceed a boolean indicating whether the contest succeeded
     */
    public void handleContest(boolean contestSucceed) {
        turnManager.dealContest(contestSucceed);
    }
}
