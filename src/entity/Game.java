package entity;

import java.util.ArrayList;

public class Game {
    private static int nextId = 0;

    private final int id;
    private int nextPlayerId = 0;
    private Board board;
    private ArrayList<Player> players;
    private ArrayList<Play> moves;
    protected LetterBag letterBag;

    public Game() {
        this.id = nextId++;
        this.board = new Board(15, 15);
        this.players = new ArrayList<>();
        this.moves = new ArrayList<>();
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
        moves.add(play);
    }

    public Play removePlay() {
        return moves.remove(moves.size() - 1);
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

    public ArrayList<Letter> getPlayerInventory(int playerId) {
        return players.get(playerId).getInventory();
    }

    public void startGame() {
        for (Player player : players) {
            player.redrawTiles(letterBag.drawLetters(7));
        }
    }
}
