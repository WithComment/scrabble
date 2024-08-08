package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.*;
import java.util.ArrayList;
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
    private int playerNumber;
    private final List<Integer> numContestFailed;
    private Play currentPlay;

    private int numContests;

    private int playerIDCounter;

    /**
     * Constructs a new Game instance.
     * Initializes the game with a unique ID, a new board, empty player list, empty play history, and a new letter bag.
     */
    public Game() {
        this.id = nextId++;
        this.letterBag = new LetterBag();
        this.board = new Board();
        this.players = new ArrayList<>();
        this.history = new ArrayList<>();
        this.leaderboard = new ArrayList<>();
        this.endTurn = false;
        this.numContestFailed = new ArrayList<>(Collections.nCopies(players.size(), 0));
        this.currentPlay = null;
        this.numContests = 0;
        this.playerIDCounter = 0;
    }

    public Game(int numOfPlayers) {
        this();
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player(playerIDCounter++));
            numContestFailed.add(0);
        }
        for (Player player : players) {
            player.addLetter(letterBag.drawLetters(7));
        }
    }

    public Game(List<String> playerNames) {
        this();
        for (String playerName : playerNames) {
            players.add(new Player(playerName, playerIDCounter++));
            numContestFailed.add(0);
        }
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
                @JsonProperty("leaderboard") List<Player> leaderboard,
                @JsonProperty("endTurn") Boolean endTurn,
                @JsonProperty("playerNumber") int playerNumber,
                @JsonProperty("numContestFailed") List<Integer> numContestFailed,
                @JsonProperty("currentPlay") Play currentPlay,
                @JsonProperty("numContests") int numContests,
                @JsonProperty("playerIDCounter") int playerIdCounter){
        this.id = id;
        this.letterBag = letterBag;
        this.board = board;
        this.players = players;
        this.history = history;
        this.leaderboard = leaderboard;
        this.endTurn = endTurn;
        this.playerNumber = playerNumber;
        this.numContestFailed = numContestFailed;
        this.currentPlay = currentPlay;
        this.numContests = numContests;
        this.playerIDCounter = playerIdCounter;
    }
    
    public int getId() {
        return id;
    }

    public List<Play> getHistory() {
        return history;
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
     * Updates the list of players by adding a new player.
     *
     * @param name the name of the player to be added
     */
    public Player addPlayer(String name) {
        Player player = new Player(name, playerIDCounter++);
        for (Letter letter : letterBag.drawLetters(7)) {
            player.addLetter(letter);
        };
        players.add(player);
        leaderboard.add(player);
        numContestFailed.add(0);
        return player;
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

    @JsonIgnore
    public int getNumPlayers() {
        return players.size();
    }
    
    public List<Integer> getNumContestFailed() {
        return numContestFailed;
    }

    /**
     * Starts the game by distributing a set number of letters to each player's inventory.
     * Each player is given 7 letters from the letter bag at the start of the game.
     */
    public void startGame() {
        startTurn();
    }

    public Board getBoard(){ 
        return board; 
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the leaderboard.
     *
     * @return the leaderboard
     */
    public List<Player> getLeaderboard() {
        List<Player> leaderboard = new ArrayList<>(players);

        Collections.sort(leaderboard, Collections.reverseOrder());

        this.leaderboard = leaderboard;
        return leaderboard;
    }

    /**
     * Starts a new turn by setting the endTurn flag to false and
     * updating the current player to the next player in the list,
     * skipping any players who have failed a contest.
     */

    public void startTurn(){
        currentPlay = new Play(getCurrentPlayer());
        this.endTurn = false;
    }

    /**
     * Updates the contest failure count for a player.
     * Increments the number of contest failures for the specified player
     * and adjusts the current player's score based on contest results.
     *
     * @param playerNumber the number of the player whose contest failure count is being updated
     */
    public void contestFailureUpdate(int playerNumber) {
        numContestFailed.set(playerNumber, numContestFailed.get(playerNumber) + 1);
    }

    /**
     * Returns the dealContest(turnManagerInputData.isContestSucceed); current player.
     *
     * @return the current player
     */
    @JsonIgnore
    public Player getCurrentPlayer() {
        return players.get(playerNumber);
    }

    /**
     * Handles the result of a contest.
     * If the contest succeeds, updates the current player's score and contest failure count.
     *
     * @param ContestSucceed a boolean indicating whether the contest succeeded
     */
    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed){
            numContestFailed.set((playerNumber), numContestFailed.get(playerNumber) + 1);
        }
        this.getCurrentPlayer().confirmTempScore();
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

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Play getCurrentPlay() {
        return currentPlay;
    }

    public void increaseNumContests() {
        numContests++;
    }

    public int getNumContests() {
        return numContests;
    }
    
    public int getPlayerIDCounter() {
        return playerIDCounter;
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

        endTurn = true;

        numContests = 0;
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
                (leaderboard.equals(other.leaderboard)) &&
                (endTurn == other.endTurn) &&
                (playerNumber == other.playerNumber) &&
                (numContestFailed.equals(other.numContestFailed)) &&
                (Objects.equals(currentPlay, other.currentPlay)) &&
                (numContests == other.numContests) &&
                (playerIDCounter == other.playerIDCounter);
    }

    @Override
    public String toString() {
        return "Game" + id;
    }
}
