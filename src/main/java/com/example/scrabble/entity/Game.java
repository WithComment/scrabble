package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a game of Scrabble.
 * A game consists of a board, a letter bag, a list of players, and a history of plays made during the game.
 * The game is responsible for managing the state of the game, including the board, players, and letter bag.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game implements Serializable {
    // Serializable vars
    private static int nextId = 0;
    private final int id; // Unique ID for the game instance
    private final LetterBag letterBag; // Bag of letters available to draw from
    private final Board board; // The game board
    private final List<Player> players; // List of players in the game
    private final List<Play> history; // History of plays made during the game
    private List<Player> leaderboard;

    // turn manager
    private Boolean endTurn;
    private Player currentPlayer;
    private int playerNumber;
    private final List<Integer> numContestFailed;
    private Play currentPlay;

    /**
     * Constructs a new Game instance.
     * Initializes the game with a unique ID, a new board, empty player list, empty play history, and a new letter bag.
     */
    public Game() {
        this.id = nextId++;
        this.board = new Board();
        this.players = new ArrayList<>();
        this.history = new ArrayList<>();
        this.letterBag = new LetterBag();
        this.endTurn = false;
        this.numContestFailed = new ArrayList<>(Collections.nCopies(players.size(), 0));
        this.currentPlay = null;
    }

    public Game(int numOfPlayers) {
        this();
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        if(numOfPlayers > 0){
            this.currentPlayer = players.getFirst();
        }
    }

    public Game(List<String> playerNames) {
        this();
        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }
        if(!players.isEmpty()){
            this.currentPlayer = players.getFirst();
        }
    }

    public void setPlayers(List<Player> players) {
        this.players.addAll(players);
        for (Player player : players) {
            player.addLetter(letterBag.drawLetters(7));
        }
    }

    @JsonCreator
    public Game(@JsonProperty("id") int id,
                @JsonProperty("letterBag") LetterBag letterBag,
                @JsonProperty("board") Board board,
                @JsonProperty("players") List<Player> players,
                @JsonProperty("history") List<Play> history,
                @JsonProperty("leaderboard") List<Player> leaderboard) {
        this.id = id;
        this.letterBag = letterBag;
        this.board = board;
        this.players = players;
        this.history = history;
        this.leaderboard = leaderboard;
    }

    /**
     * Gets the unique ID of the game.
     *
     * @return The unique ID of the game.
     */
    public int getId() {
        return id;
    }

    public List<Play> getHistory() {
        return history;
    }

    /**
     * Sets a tile at the specified position on the board.
     *
     * @param x    The x-coordinate of the cell.
     * @param y    The y-coordinate of the cell.
     * @param tile The tile to place at the specified position.
     */
    public void setBoardCell(int x, int y, Tile tile) {
        board.setCell(x, y, tile);
    }

    /**
     * Retrieves the tile at the specified position on the board.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The tile at the specified position.
     */
    @JsonIgnore
    public Tile getBoardCell(int x, int y) {
        return board.getCell(x, y);
    }

    public LetterBag getLetterBag() {
        return letterBag;
    }

    /**
     * Gets the specific player by their ID.
     *
     * @param playerId The ID of the player.
     * @return The specified player.
     */
    @JsonIgnore
    public Player getPlayer(int playerId) {
        return players.get(playerId);
    }

    /**
     * Adds a new player to the game.
     * Initializes the player with a unique ID and adds them to the player list.
     */
    public void addPlayer() {
        Player player = new Player();
        players.add(player);
        this.numContestFailed.add(0);
    }

    /**
     * Adds a play to the game's history.
     *
     * @param play The play to add to the history.
     */
    public void addPlay(Play play) {
        history.add(play);
    }

    /**
     * Returns the last play from the game's history.
     *
     * @return The last play made in the game, or null if the game has no plays.
     */
    @JsonIgnore
    public Play getLastPlay() {
        if (history.isEmpty()) {
            return null;
        }
        return history.getLast();
    }

    /**
     * Removes and returns the last play from the game's history.
     *
     * @return The last play made in the game, or null if the game has no plays.
     */
    public Play removeLastPlay() {
        if (history.isEmpty()) {
            return null;
        }
        return history.removeLast();
    }

    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    @JsonIgnore
    public int getNumPlayers() {
        return players.size();
    }

    /**
     * Gets a list of player IDs for all players in the game.
     *
     * @return A list of player IDs.
     */
    @JsonIgnore
    public List<Integer> getPlayerIds() {
        List<Integer> playerIds = new ArrayList<>();
        for (Player player : players) {
            playerIds.add(player.getId());
        }
        return playerIds;
    }

    /**
     * Gets the score of a specific player by their ID.
     *
     * @param playerId The ID of the player.
     * @return The score of the specified player.
     */
    @JsonIgnore
    public int getPlayerScore(int playerId) {
        return players.get(playerId).getScore();
    }


    /**
     * Gets a list of scores for all players in the game.
     *
     * @return An List<Integer> containing the scores of all players.
     */
    @JsonIgnore
    public List<Integer> getPlayerScore() {
        List<Integer> scores = new ArrayList<>();
        for (Player player : players) {
            scores.add(player.getScore());
        }
        return scores;
    }

    /**
     * Retrieves the inventory of letters for a specific player.
     *
     * @param playerId The ID of the player whose inventory is requested.
     * @return A List<Letter> representing the player's current inventory of letters.
     */
    @JsonIgnore
    public List<Letter> getPlayerInventory(int playerId) {
        return players.get(playerId).getInventory();
    }

    /**
     * Starts the game by distributing a set number of letters to each player's inventory.
     * Each player is given 7 letters from the letter bag at the start of the game.
     */
    public void startGame() {
        for (Player player : players) {
            player.addLetter(letterBag.drawLetters(7));
        }
        startTurn();
    }

    /**
     * Returns the board of the game.
     *
     * @return the board of the game
     */
    public Board getBoard(){ return board; }

    /**
     * Returns the list of players in the game.
     *
     * @return the list of players in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the leaderboard.
     *
     * @return the leaderboard
     */
    public List<Player> getLeaderboard() {
        return leaderboard;
    }

    /**
     * Sets the leaderboard.
     *
     * @param leaderboard the leaderboard to set
     */
    public void setLeaderboard(List<Player> leaderboard) {
        this.leaderboard = leaderboard;
    }

    /**
     * Starts a new turn by setting the endTurn flag to false and
     * updating the current player to the next player in the list,
     * skipping any players who have failed a contest.
     */

    public void startTurn(){
        currentPlay = new Play(currentPlayer);
        System.out.println(currentPlay);
        this.endTurn = false;
    }

    /**
     * Updates the contest failure count for a player.
     * Increments the number of contest failures for the specified player
     * and adjusts the current player's score based on contest results.
     *
     * @param PlayerNumber the number of the player whose contest failure count is being updated
     */
    public void contestFailureUpdate(int PlayerNumber) {
        int CurrentFailure = numContestFailed.get(PlayerNumber);
        numContestFailed.set(PlayerNumber, CurrentFailure + 1);
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.BeContested();
    }

    /**
     * Returns the dealContest(turnManagerInputData.isContestSucceed); current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        currentPlayer = players.get(playerNumber);
        return currentPlayer;
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
        if(players.isEmpty()){
            this.currentPlayer = player;
        }
        players.add(player);
        numContestFailed.add(0);
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

    /**
     * Ends the current turn by setting the endTurn flag to true.
     */
    public void endTurn() {
        while (numContestFailed.get((playerNumber + 1) % players.size()) > 0) {
            int NumContestFailedOfNextPlayer = numContestFailed.get((playerNumber + 1) % players.size());
            numContestFailed.set((playerNumber + 1) % players.size(), NumContestFailedOfNextPlayer - 1);
            playerNumber = (playerNumber + 1) % players.size();
        }
        playerNumber = (playerNumber + 1) % players.size();
        currentPlayer = players.get(playerNumber);

        endTurn = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){return true;}

        if (obj == null || getClass() != obj.getClass()){return false;}

        Game other = (Game) obj;

        return  (id == other.id) &&
                (letterBag.equals(other.letterBag)) &&
                (board.equals(other.board)) &&
                (players.equals(other.players)) &&
                (history.equals(other.history)) &&
                (leaderboard.equals(other.leaderboard));
    }
}
