package use_case.EndTurn;

import entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the turns of players in the game.
 * Keeps track of the current player, manages the end of turns,
 * and handles the contesting process.
 */

public class TurnManager implements EndTurn, DealingContest, StartTurn {
    public Boolean endTurn;
    private Player CurrentPlayer;
    public int PlayerNumber;
    private final ArrayList<Player> Players;
    public ArrayList<Integer> NumContestFailed;

    /**
     * Constructs a TurnManager with a list of players.
     * Initializes the endTurn flag, current player, players list, and contest failure counts.
     *
     *  List of players participating in the game
     *  Duplicate list of players (not used in this implementation)
     */

    public TurnManager(List<Player> players, ArrayList<Player> players1) {
        this.endTurn = false;
        this.CurrentPlayer = null;
        this.Players = new ArrayList<>();
        this.NumContestFailed = new ArrayList<>();
    }

    /**
     * Returns the current player.
     *
     * @return the current player
     */

    public Player getCurrentPlayer() {
        this.CurrentPlayer = Players.get(this.PlayerNumber);
        return this.CurrentPlayer;
    }

    /**
     * Ends the current turn by setting the endTurn flag to true.
     */

    @Override
    public void endTurn() {
        this.endTurn = true;
        // Additional game state updates can be
    }

    /**
     * Starts a new turn by setting the endTurn flag to false and
     * updating the current player to the next player in the list,
     * skipping any players who have failed a contest.
     */

    @Override
    public void startTurn(){

        while (NumContestFailed.get((PlayerNumber + 1) % Players.size()) > 0) {
            int NumContestFailedOfNextPlayer = NumContestFailed.get((PlayerNumber + 1) % Players.size());
            NumContestFailed.set((PlayerNumber + 1) % Players.size(), NumContestFailedOfNextPlayer - 1);
            PlayerNumber = (PlayerNumber + 1) % Players.size();
        }
        PlayerNumber = (PlayerNumber + 1) % Players.size();
        // Notify the front-end or other players that the turn has ended and it's the next player's turn
        CurrentPlayer = Players.get(PlayerNumber);
        System.out.println("It's now player " + PlayerNumber + "'s turn.");
        System.out.println("It's now player " + getCurrentPlayer().getId() + "'s turn.");
        this.endTurn = false;

    }

    /**
     * Handles the result of a contest.
     * If the contest succeeds, updates the current player's score and contest failure count.
     *
     * @param ContestSucceed a boolean indicating whether the contest succeeded
     */

    @Override
    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed){
            this.CurrentPlayer.BeContested();
            NumContestFailed.set((PlayerNumber), NumContestFailed.get((PlayerNumber)));
        }
        this.CurrentPlayer.NotContested();
        System.out.println("Player " + this.CurrentPlayer.getId() + " contest result: " + (ContestSucceed ? "Valid" : "Invalid"));
    }
}







