package com.example.scrabble.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    private LetterBag letterBag; // Bag of letters available to draw from
    private Board board; // The game board
    private List<Player> players; // List of players in the game
    private List<Play> history; // History of plays made during the game
    private TurnManager turnManager;
    private List<Player> leaderboard;

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
        this.turnManager = new TurnManager(new ArrayList<>());
    }

    public Game(int numOfPlayers) {
        this();
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
    }

    public Game(List<String> playerNames) {
        this();
        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }
    }

    public void setPlayers(List<Player> players) {
        this.players.addAll(players);
        for(Player player : players){
            this.turnManager.addPlayer(player);
        }
        for (Player player : players) {
            player.addLetter(letterBag.drawLetters(7));
        }
    }

    public int getId() {
        return id;
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
    public Player getPlayer(int playerId) {
        return players.get(playerId);
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    public Play getCurrentPlay() {
        return turnManager.getCurrentPlay();
    }

    /**
     * Adds a new player to the game.
     * Initializes the player with a unique ID and adds them to the player list.
     *
     * @return The player that was added to the game.
     */
    public Player addPlayer() {
        Player player = new Player();
        players.add(player);
        return player;
    }

    public Player addPlayer(Player player) {
        players.add(player);
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
     * Returns the last play from the game's history.
     *
     * @return The last play made in the game, or null if the game has no plays.
     * @return The last play made in the game.
     */
    public Play getLastPlay() {
        if (history.isEmpty()) {
            return null;
        }
        return history.get(history.size() - 1);
    }

    /**
     * Removes and returns the last play from the game's history.
     *
     * @return The last play made in the game, or null if the game has no plays.
     * @return The last play made in the game.
     */
    public Play removeLastPlay() {
        if (history.isEmpty()) {
            return null;
        }
        return history.remove(history.size() - 1);
    }

    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    public int getNumPlayers() {
        return players.size();
    }

    /**
     * Gets a list of player IDs for all players in the game.
     *
     * @return A list of player IDs.
     */
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
    public int getPlayerScore(int playerId) {
        return players.get(playerId).getScore();
    }


    /**
     * Gets a list of scores for all players in the game.
     *
     * @return An List<Integer> containing the scores of all players.
     */
    public List<Integer> getPlayerScore() {
        List<Integer> scores = new ArrayList<>();
        for (Player player : players) {
            scores.add(player.getScore());
        }
        return scores;
    }

    /**
     * Update players to TurnManager
     */
    public void SetPlayerToTurnManager(TurnManager turnManager) {
        for (Player player : players) {
            this.turnManager.addPlayer(player);
        }
    }

//    /**
//     * Updates the score of a specific player based on a play.
//     *
//     * @param playerID The ID of the player whose score is to be updated.
//     * @param play     The play containing the score to add to the player's total score.
//     */
//    public void updatePlayerScore(int playerID, Play play) {
//        ArrayList<Integer> scores = new ArrayList<>();
//        scores.add(play.getScore());
//        players.get(playerID).updateScore(scores);
//    }

    /**
     * Retrieves the inventory of letters for a specific player.
     *
     * @param playerId The ID of the player whose inventory is requested.
     * @return A List<Letter> representing the player's current inventory of letters.
     */
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
        turnManager.startTurn();
    }

    /**
     * Returns the TurnManager of the game.
     *
     * @return the TurnManager of the game
     */
    public TurnManager getTurnManager() {
        return turnManager;
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
}
