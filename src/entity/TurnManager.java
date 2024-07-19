package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the turns of players in the game.
 * Keeps track of the current player, manages the end of turns,
 * and handles the contesting process.
 */
public class TurnManager {
    private final List<Player> Players;
    private Boolean endTurn;
    private Player CurrentPlayer;
    private int PlayerNumber;
    private List<Integer> NumContestFailed;

    /**
     * Constructs a TurnManager with an initial state.
     * Initializes the endTurn flag, current player, players list, and contest failure counts.
     */
    public TurnManager(List<Player> players) {
        this.endTurn = false;
        this.CurrentPlayer = players.get(0);
        this.Players = players;
        this.NumContestFailed = new ArrayList<Integer>(players.size());
    }

    /**
     * Ends the current turn by setting the endTurn flag to true.
     */
    public void endTurn() {
        endTurn = true;
    }

    /**
     * Starts a new turn by setting the endTurn flag to false and
     * updating the current player to the next player in the list,
     * skipping any players who have failed a contest.
     */

    public void startTurn(){

        while (NumContestFailed.get((PlayerNumber + 1) % Players.size()) > 0) {
            int NumContestFailedOfNextPlayer = NumContestFailed.get((PlayerNumber + 1) % Players.size());
            NumContestFailed.set((PlayerNumber + 1) % Players.size(), NumContestFailedOfNextPlayer - 1);
            PlayerNumber = (PlayerNumber + 1) % Players.size();
        }
        PlayerNumber = (PlayerNumber + 1) % Players.size();
        // Notify the front-end or other players that the turn has ended and it's the next player's turn
        CurrentPlayer = Players.get(PlayerNumber);
//        System.out.println("It's now player " + PlayerNumber + "'s turn.");
//        System.out.println("It's now player " + getCurrentPlayer().getId() + "'s turn.");
        this.endTurn = false;

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
     * If the contest succeeds, updates the current player's score and contest failure count.
     *
     * @param ContestSucceed a boolean indicating whether the contest succeeded
     */

    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed){
            this.CurrentPlayer.BeContested();
            NumContestFailed.set((PlayerNumber), NumContestFailed.get((PlayerNumber)));
        }
        this.CurrentPlayer.NotContested();
//        System.out.println("Player " + this.CurrentPlayer.getId() + " contest result: " + (ContestSucceed ? "Valid" : "Invalid"));
    }

    public void updatePlayer(Player player) {
        Players.add(player);
    }

    public boolean isEndTurn() {
        return endTurn;
    }

    public int getPlayersNumContestFailed(int PlayerNumber) {
        return NumContestFailed.get(PlayerNumber);
    }

    public int getCurrentPlayerNum() {
        return PlayerNumber;
    }
}

