package entity;

import java.io.*;
import java.util.ArrayList;

/**
 * Represents a game of Scrabble.
 * A game has an ID, a board, a list of players, a history of plays, and a letter bag.
 */
public class Game implements Serializable {
    // Serializable vars
    private static final long serialVersionUID = 1L; // Unique version identifier for serialization
    private static int nextId = 0; // Static counter to assign unique IDs to each game instance
    private final int id; // Unique ID for the game instance
    private int nextPlayerId = 0; // Counter to assign unique IDs to players within this game
    private Board board; // The game board
    private ArrayList<Player> players; // List of players in the game
    private ArrayList<Play> history; // History of plays made during the game
    protected LetterBag letterBag; // Bag of letters available to draw from

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
    }

    /**
     * Gets the unique ID of the game.
     * @return The unique ID of the game.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a tile at the specified position on the board.
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @param tile The tile to place at the specified position.
     */
    public void setBoardCell(int x, int y, Tile tile) {
        board.setCell(x, y, tile);
    }

    /**
     * Retrieves the tile at the specified position on the board.
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The tile at the specified position.
     */
    public Tile getBoardCell(int x, int y) {
        return board.getCell(x, y);
    }

    /**
     * Adds a new player to the game.
     * Initializes the player with a unique ID and adds them to the player list.
     */
    public void addPlayer() {
        players.add(new Player(nextPlayerId++));
    }

    /**
     * Adds a play to the game's history.
     * @param play The play to add to the history.
     */
    public void addPlay(Play play) {
        history.add(play);
    }

    /**
     * Removes and returns the last play from the game's history.
     * @return The last play made in the game.
     */
    public Play removePlay() {
        return history.remove(history.size() - 1);
    }

    /**
     * Gets the number of players in the game.
     * @return The number of players.
     */
    public int getNumPlayers() {
        return players.size();
    }

    /**
     * Gets a list of player IDs for all players in the game.
     * @return A list of player IDs.
     */
    public ArrayList<Integer> getPlayerIds() {
        ArrayList<Integer> playerIds = new ArrayList<>();
        for (Player player : players) {
            playerIds.add(player.getId());
        }
        return playerIds;
    }

    /**
     * Gets the score of a specific player by their ID.
     * @param playerId The ID of the player.
     * @return The score of the specified player.
     */
    public int getPlayerScore(int playerId) {
        return players.get(playerId).getScore();
    }

    /**
     * Gets a list of scores for all players in the game.
     * @return An ArrayList<Integer> containing the scores of all players.
     */
    public ArrayList<Integer> getPlayerScore() {
        ArrayList<Integer> scores = new ArrayList<>();
        for (Player player : players) {
            scores.add(player.getScore());
        }
        return scores;
    }

    /**
     * Updates the score of a specific player based on a play.
     * @param playerID The ID of the player whose score is to be updated.
     * @param play The play containing the score to add to the player's total score.
     */
    public void updatePlayerScore(int playerID, Play play) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(play.getScore());
        players.get(playerID).updateScore(scores);
    }

    /**
     * Retrieves the inventory of letters for a specific player.
     * @param playerId The ID of the player whose inventory is requested.
     * @return An ArrayList<Letter> representing the player's current inventory of letters.
     */
    public ArrayList<Letter> getPlayerInventory(int playerId) {
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
    }
}
