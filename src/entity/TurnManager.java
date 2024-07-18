package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the turns of players in the game.
 * Keeps track of the current player, manages the end of turns,
 * and handles the contesting process.
 */
public class TurnManager {
    private final List<Player> players;
    private Boolean endTurn;
    private Player currentPlayer;
    private int playerNumber;
    private List<Integer> numContestFailed;

    /**
     * Constructs a TurnManager with an initial state.
     * Initializes the endTurn flag, current player, players list, and contest failure counts.
     */
    public TurnManager(List<Player> players) {
        this.endTurn = false;
        this.currentPlayer = null;
        this.players = players;
        this.numContestFailed = new ArrayList<Integer>(players.size());
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
    public void CheckAndEndTurn() {
        Player currentPlayer = ReturnCurrentPlayer();
        currentPlayer.NotContested();
        while (numContestFailed.get((playerNumber + 1) % players.size()) > 0) {
            int NumContestFailedOfNextPlayer = numContestFailed.get((playerNumber + 1) % players.size());
            numContestFailed.set((playerNumber + 1) % players.size(), NumContestFailedOfNextPlayer - 1);
            playerNumber = (playerNumber + 1) % players.size();
        }
        playerNumber = (playerNumber + 1) % players.size();
        // Notify the front-end or other players that the turn has ended and it's the next player's turn
        this.currentPlayer = players.get(playerNumber);
        System.out.println("It's now player " + playerNumber + "'s turn.");
    }

    /**
     * Updates the contest failure count for a player.
     * Increments the number of contest failures for the specified player
     * and adjusts the current player's score based on contest results.
     *
     * @param PlayerNumber the number of the player whose contest failure count is being updated
     */
    public void ContestFailureUpdate(int PlayerNumber) {
        int CurrentFailure = numContestFailed.get(PlayerNumber);
        numContestFailed.set(PlayerNumber, CurrentFailure + 1);
        Player currentPlayer = ReturnCurrentPlayer();
        currentPlayer.BeContested();
    }

    /**
     * Returns the current player.
     *
     * @return the current player
     */
    public Player ReturnCurrentPlayer() {
        currentPlayer = players.get(playerNumber);
        return currentPlayer;
    }

    /**
     * Handles the result of a contest.
     * If the contest succeeds, the current player's contest failure count is incremented.
     *
     * @param ContestSucceed a boolean indicating whether the contest succeeded
     */
    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed) {
            numContestFailed.add(playerNumber);
        }
    }

    public void updatePlayer(Player player) {
        players.add(player);
    }
}

