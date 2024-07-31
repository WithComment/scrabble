package com.example.scrabble.entity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the turns of players in the game.
 * Keeps track of the current player, manages the end of turns,
 * and handles the contesting process.
 */
public class TurnManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    private List<Player> Players;
    private Boolean endTurn;
    private Player CurrentPlayer;
    private int PlayerNumber;
    private List<Integer> NumContestFailed;
    private Play CurrentPlay;

    /**
     * Constructs a TurnManager with an initial state.
     * Initializes the endTurn flag, current player, players list, and contest failure counts.
     */
    public TurnManager(List<Player> players) {
        this.endTurn = false;
        this.CurrentPlayer = players.getFirst();
        this.Players = players;
        this.NumContestFailed = new ArrayList<Integer>(Collections.nCopies(players.size(), 0));
        this.CurrentPlay = null;
    }


    public TurnManager(JSONObject json){
        this.parseJSON(json);
    }

    private void parseJSON(JSONObject json){
        this.Players = (List<Player>) json.getJSONObject("Players");
        this.NumContestFailed = new ArrayList<Integer>(Collections.nCopies(this.Players.size(), 0));
        this.endTurn = false;
        this.CurrentPlayer = Players.getFirst();
        this.PlayerNumber = 0;
        this.CurrentPlay = null;


    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        JSONObject jsonObject = new JSONObject(this);
        out.writeChars(jsonObject.toString());
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        JSONObject json = new JSONObject(in.readUTF());
        this.parseJSON(json);
    }

    /**
     * Ends the current turn by setting the endTurn flag to true.
     */
    public void endTurn() {

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

        endTurn = true;
    }

    /**
     * Starts a new turn by setting the endTurn flag to false and
     * updating the current player to the next player in the list,
     * skipping any players who have failed a contest.
     */

    public void startTurn(){
        CurrentPlay = new Play(CurrentPlayer);
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
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.BeContested();
    }

    /**
     * Returns thedealContest(turnManagerInputData.isContestSucceed); current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        CurrentPlayer = Players.get(PlayerNumber);
        return CurrentPlayer;
    }

    /**
     * Returns the cause of this exception.
     * @return The cause of this exception.
     */
    public List<Player> GetPlayers() {
        return this.Players;
    }

     /**
     * Handles the result of a contest.
     * If the contest succeeds, updates the current player's score and contest failure count.
     *
     * @param ContestSucceed a boolean indicating whether the contest succeeded
     */
    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed){
            NumContestFailed.set((PlayerNumber), NumContestFailed.get((PlayerNumber)));
        }
        this.CurrentPlayer.confirmTempScore();
//        System.out.println("Player " + this.CurrentPlayer.getId() + " contest result: " + (ContestSucceed ? "Valid" : "Invalid"));
    }

    /**
     * Updates the list of players by adding a new player.
     *
     * @param player the player to be added
     */
    public void updatePlayer(Player player) {
        Players.add(player);
    }

    /**
     * Checks if the turn has ended.
     *
     * @return true if the turn has ended, false otherwise
     */
    public boolean isEndTurn() {
        return endTurn;
    }

    /**
     * Returns the number of contest failures for a specified player.
     *
     * @param PlayerNumber the number of the player
     * @return the number of contest failures for the player
     */
    public int getPlayersNumContestFailed(int PlayerNumber) {
        return NumContestFailed.get(PlayerNumber);
    }

    /**
     * Returns the number of the current player.
     *
     * @return the number of the current player
     */
    public int getCurrentPlayerNum() {
        return PlayerNumber;
    }

    /**
     * Returns the current play.
     *
     * @return the current play
     */
    public Play getCurrentPlay() {
        return CurrentPlay;
    }
}
