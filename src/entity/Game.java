package entity;

import java.util.ArrayList;

public class Game {
    private static int nextId = 0;

    private final int id;
    private Board board;
    private ArrayList<Player> players;
    private ArrayList<Play> moves;
    private LetterBag letterBag;

    public Game() {
        this.id = nextId++;
        this.board = new Board();
        this.players = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.letterBag = new LetterBag();
    }

    public int getId() {
        return id;
    }
}
