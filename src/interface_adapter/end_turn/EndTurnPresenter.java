package interface_adapter.end_turn;

import entity.Player;

/**
 * Presenter for the EndTurn use case.
 * Formats and prepares the data for the view.
 */
public class EndTurnPresenter {

    /**
     * Prepares the end turn message.
     *
     * @param player the player whose turn just ended
     * @return a string message indicating the end of the player's turn
     */
    public String prepareEndTurnMessage(Player player) {
        return "Player " + player.getId() + " has ended their turn.";
    }

    /**
     * Prepares the start turn message.
     *
     * @param player the player whose turn is starting
     * @return a string message indicating the start of the player's turn
     */
    public String prepareStartTurnMessage(Player player) {
        return "It's now Player " + player.getId() + "'s turn.";
    }

    /**
     * Prepares the contest result message.
     *
     * @param player the player who was contested
     * @param contestSucceed a boolean indicating whether the contest succeeded
     * @return a string message indicating the result of the contest
     */
    public String prepareContestResultMessage(Player player, boolean contestSucceed) {
        return "Player " + player.getId() + " contest result: " + (contestSucceed ? "Valid" : "Invalid");
    }
}

