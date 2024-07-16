package entity;

import java.io.*;
import java.util.ArrayList;

public class Game implements Serializable {
    // Serializable vars
    private static final long serialVersionUID = 1L;
    private static int nextId = 0;
    private final int id;
    private int nextPlayerId = 0;
    private Board board;
    private ArrayList<Player> players;
    private ArrayList<Play> history;
    protected LetterBag letterBag;

    public Game() {
        this.id = nextId++;
        this.board = new Board();
        this.players = new ArrayList<>();
        this.history = new ArrayList<>();
        this.letterBag = new LetterBag();
    }

    public int getId() {
        return id;
    }

    public void setBoardCell(int x, int y, Tile tile) {
        board.setCell(x, y, tile);
    }

    public Tile getBoardCell(int x, int y) {
        return board.getCell(x, y);
    }

    public void addPlayer() {

        players.add(new Player(nextPlayerId++));
    }

    public void addPlay(Play play) {

        history.add(play);
    }

    public Play removePlay() {
        return history.remove(history.size() - 1);
    }

    public int getNumPlayers() {
        return players.size();
    }

    public ArrayList<Integer> getPlayerIds() {
        ArrayList<Integer> playerIds = new ArrayList<>();
        for (Player player : players) {
            playerIds.add(player.getId());
        }
        return playerIds;
    }

    public int getPlayerScore(int playerId) {
        return players.get(playerId).getScore();
    }

    public ArrayList<Integer> getPlayerScore() {
        ArrayList<Integer> scores = new ArrayList<>();
        for (Player player : players) {
            scores.add(player.getScore());
        }
        return scores;
    }

    public void updatePlayerScore(int playerID, Play play) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(play.getScore());
        players.get(playerID).updateScore(scores);
    }

    public ArrayList<Letter> getPlayerInventory(int playerId) {
        return players.get(playerId).getInventory();
    }

    public void startGame() {
        for (Player player : players) {
            player.addLetter(   letterBag.drawLetters(7));
        }
    }
}
