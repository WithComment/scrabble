package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the turns of players in the game.
 * Keeps track of the current player, manages the end of turns,
 * and handles the contesting process.
 */
public class TurnManager {
    private final List<Player> players;
    private Boolean endTurn;
    private Player currentPlayer;
    private int currentPlayerId;
    private Map<Integer, Integer> numContestFailed;

    /**
     * Constructs a TurnManager with an initial state.
     * Initializes the endTurn flag, current player, players list, and contest failure counts.
     */
    public TurnManager(List<Player> players) {
        this.endTurn = false;
        this.currentPlayer = null;
        this.players = players;
        numContestFailed = new HashMap<>();
    }

    private void incrementContestFail(int playerNumber) {
        int currentFail = numContestFailed.getOrDefault(playerNumber, 0);
        numContestFailed.put(playerNumber, currentFail + 1);
    }

    private void decreaseContestFail(int playerNumber) {
        int currentFail = numContestFailed.getOrDefault(playerNumber, 0);
        numContestFailed.put(playerNumber, currentFail - 1);
    }

    private int getNextPlayerId() {
        return (currentPlayerId + 1) % players.size();
    }

    /**
     * Ends the current turn by setting the endTurn flag to true.
     */
    public void EndTurn() {
        endTurn = true;
    }

    /**
     * Starts a new turn by setting the endTurn flag to false.
     */
    public void startTurn() {
        endTurn = false;
    }

    /**
     * Checks the conditions and ends the current turn.
     * Updates the current player to the next player in the list,
     * skipping any players who have failed a contest.
     */
    public void checkAndEndTurn() {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.NotContested();
        int nextPlayerId = getNextPlayerId();
        while (numContestFailed.getOrDefault(nextPlayerId, 0) > 0) {
            decreaseContestFail(nextPlayerId);
            currentPlayerId = nextPlayerId;
        }
        currentPlayerId = nextPlayerId;
        // Notify the front-end or other players that the turn has ended and it's the next player's turn
        this.currentPlayer = players.get(currentPlayerId);
        System.out.println("It's now player " + currentPlayerId + "'s turn.");
    }

    /**
     * Updates the contest failure count for a player.
     * Increments the number of contest failures for the specified player
     * and adjusts the current player's score based on contest results.
     *
     * @param playerId the number of the player whose contest failure count is being updated
     */
    public void ContestFailureUpdate(int playerId) {
        incrementContestFail(playerId);
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.BeContested();
    }

    /**
     * Returns the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        currentPlayer = players.get(currentPlayerId);
        return currentPlayer;
    }

    /**
     * Handles the result of a contest.
     * If the contest succeeds, the current player's contest failure count is incremented.
     *
     * @param contestSucceed a boolean indicating whether the contest succeeded
     */
    public void dealContest(boolean contestSucceed) {
        if (contestSucceed) {
            incrementContestFail(currentPlayerId);
        }
    }

    public void updatePlayer(Player player) {
        players.add(player);
    }
}

