package entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    // Serializable vars
    private static final long serialVersionUID = 1L; // Unique version identifier for serialization
    private static int nextId = 0; // Static counter to assign unique IDs to each game instance
    private final int id; // Unique ID for the game instance
    private LetterBag letterBag; // Bag of letters available to draw from
    private int nextPlayerId = 0; // Counter to assign unique IDs to players within this game
    private Board board; // The game board
    private List<Player> players; // List of players in the game
    private List<Play> history; // History of plays made during the game
    private TurnManager turnManager;

    /**
     * Constructs a new Game instance.
     * Initializes the game with a unique ID, a new board, empty player list, empty play history, and a new letter bag.
     */
    public Game(List<Player> players) {
        this.id = nextId++;
        this.board = new Board();
        this.players = new ArrayList<Player>();
        this.players.addAll(players);
        this.history = new ArrayList<>();
        this.letterBag = new LetterBag();
        this.turnManager = new TurnManager(this.players);
    }

    /**
     * Gets the unique ID of the game.
     *
     * @return The unique ID of the game.
     */
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
     * Adds a new player to the game.
     * Initializes the player with a unique ID and adds them to the player list.
     *
     * @return The player that was added to the game.
     */
    public Player addPlayer() {
        Player player = new Player(0);
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
     * @return The last play made in the game.
     */
    public Play getLastPlay() {
        return history.get(history.size() - 1);
    }

    /**
     * Removes and returns the last play from the game's history.
     *
     * @return The last play made in the game.
     */
    public Play removeLastPlay() {
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
    public ArrayList<Integer> getPlayerIds() {
        ArrayList<Integer> playerIds = new ArrayList<>();
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
     * Update players to TurnManager
     */
    public void SetPlayerToTurnManager(TurnManager turnManager) {
        for (Player player : players) {
            this.turnManager.updatePlayer(player);
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

    /**
     * Returns the TurnManager of the game.
     *
     * @return the TurnManager of the game
     */
    public TurnManager getTurnManager() {
        return turnManager;
    }

    public Board getBoard(){ return board; }

    public List<Player> getPlayers() {
        return players;
    }
}
