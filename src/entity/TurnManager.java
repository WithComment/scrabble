package entity;

import java.util.ArrayList;

/**
 * Manages the turns of players in the game.
 * Keeps track of the current player, manages the end of turns,
 * and handles the contesting process.
 */
public class TurnManager {
    private Boolean endTurn;
    private Player CurrentPlayer;
    private int PlayerNumber;
    private final ArrayList<Player> Players;
    private ArrayList<Integer> NumContestFailed;

    /**
     * Constructs a TurnManager with an initial state.
     * Initializes the endTurn flag, current player, players list, and contest failure counts.
     */
    public TurnManager() {
        this.endTurn = false;
        this.CurrentPlayer = null;
        this.Players = new ArrayList<>();
        this.NumContestFailed = new ArrayList<>();
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
        while (NumContestFailed.get((PlayerNumber + 1) % Players.size()) > 0) {
            int NumContestFailedOfNextPlayer = NumContestFailed.get((PlayerNumber + 1) % Players.size());
            NumContestFailed.set((PlayerNumber + 1) % Players.size(), NumContestFailedOfNextPlayer - 1);
            PlayerNumber = (PlayerNumber + 1) % Players.size();
        }
        PlayerNumber = (PlayerNumber + 1) % Players.size();
        // Notify the front-end or other players that the turn has ended and it's the next player's turn
        CurrentPlayer = Players.get(PlayerNumber);
        System.out.println("It's now player " + PlayerNumber + "'s turn.");
    }

    /**
     * Updates the contest failure count for a player.
     * Increments the number of contest failures for the specified player
     * and adjusts the current player's score based on contest results.
     *
     * @param PlayerNumber the number of the player whose contest failure count is being updated
     */
    public void ContestFailureUpdate(int PlayerNumber) {
        int CurrentFailure = NumContestFailed.get(PlayerNumber);
        NumContestFailed.set(PlayerNumber, CurrentFailure + 1);
        Player currentPlayer = ReturnCurrentPlayer();
        currentPlayer.BeContested();
    }

    /**
     * Returns the current player.
     *
     * @return the current player
     */
    public Player ReturnCurrentPlayer() {
        CurrentPlayer = Players.get(PlayerNumber);
        return CurrentPlayer;
    }

    /**
     * Handles the result of a contest.
     * If the contest succeeds, the current player's contest failure count is incremented.
     *
     * @param ContestSucceed a boolean indicating whether the contest succeeded
     */
    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed) {
            NumContestFailed.add(PlayerNumber);
        }
    }
}

