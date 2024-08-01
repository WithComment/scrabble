package com.example.scrabble.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

/**
 * Manages the turns of players in the game.
 * Keeps track of the current player, manages the end of turns,
 * and handles the contesting process.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnManager implements Serializable {
    private List<Player> player;
    private Boolean endTurn;
    private Player currentPlayer;
    private int playerNumber;
    private List<Integer> numContestFailed;
    private Play currentPlay;

    /**
     * Constructs a TurnManager with an initial state.
     * Initializes the endTurn flag, current player, players list, and contest failure counts.
     */
    public TurnManager() {}

    public TurnManager(List<Player> players) {
        this.endTurn = false;
        this.player = players;
        this.numContestFailed = new ArrayList<Integer>(Collections.nCopies(players.size(), 0));
        this.currentPlay = null;
        if(!players.isEmpty()) {
            this.currentPlayer = players.get(0);
        }
    }

    public TurnManager(JSONObject json){
        this.parseJSON(json);
    }

    private void parseJSON(JSONObject json){
        this.player = (List<Player>) json.getJSONObject("Players");
        this.numContestFailed = new ArrayList<Integer>(Collections.nCopies(this.player.size(), 0));
        this.endTurn = false;
        this.currentPlayer = player.getFirst();
        this.playerNumber = 0;
        this.currentPlay = null;
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

        while (numContestFailed.get((playerNumber + 1) % player.size()) > 0) {
            int NumContestFailedOfNextPlayer = numContestFailed.get((playerNumber + 1) % player.size());
            numContestFailed.set((playerNumber + 1) % player.size(), NumContestFailedOfNextPlayer - 1);
            playerNumber = (playerNumber + 1) % player.size();
        }
        playerNumber = (playerNumber + 1) % player.size();
        currentPlayer = player.get(playerNumber);
        endTurn = true;
    }

    /**
     * Starts a new turn by setting the endTurn flag to false and
     * updating the current player to the next player in the list,
     * skipping any players who have failed a contest.
     */

    public void startTurn(){
        currentPlay = new Play(currentPlayer);
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
        int CurrentFailure = numContestFailed.get(PlayerNumber);
        numContestFailed.set(PlayerNumber, CurrentFailure + 1);
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.BeContested();
        currentPlay.setFailedContest(true);
    }

    /**
     * Returns thedealContest(turnManagerInputData.isContestSucceed); current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        currentPlayer = player.get(playerNumber);
        return currentPlayer;
    }

    /**
     * Returns the cause of this exception.
     * @return The cause of this exception.
     */
    public List<Player> GetPlayers() {
        return this.player;
    }

     /**
     * Handles the result of a contest.
     * If the contest succeeds, updates the current player's score and contest failure count.
     *
     * @param ContestSucceed a boolean indicating whether the contest succeeded
     */
    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed){
            numContestFailed.set((playerNumber), numContestFailed.get((playerNumber)));
        }
        this.currentPlayer.confirmTempScore();
//        System.out.println("Player " + this.CurrentPlayer.getId() + " contest result: " + (ContestSucceed ? "Valid" : "Invalid"));
    }

    /**
     * Updates the list of players by adding a new player.
     *
     * @param player the player to be added
     */
    public void addPlayer(Player player) {
        if(player.isEmpty()){
            this.currentPlayer = player;
        }
        player.add(player);
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
        return numContestFailed.get(PlayerNumber);
    }

    /**
     * Returns the number of the current player.
     *
     * @return the number of the current player
     */
    public int getCurrentPlayerNum() {
        return playerNumber;
    }

    /**
     * Returns the current play.
     *
     * @return the current play
     */
    public Play getCurrentPlay() {
        return currentPlay;
    }
}

