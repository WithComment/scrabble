package use_case.remove_letter;

import entity.Board;
import entity.Play;
import entity.Player;
import entity.Tile;

public class RemoveLetterInputData {
    private int x;
    private int y;
    private Player player;
    private Board board;
    private Tile selectedTile;
    public RemoveLetterInputData(
            Player player,
            Board board,
            Tile selectedTile,
            int x,
            int y
    ) {
        this.player = player;
        this.board = board;
        this.selectedTile = selectedTile;
        this.x = x;
        this.y = y;
    }

    public Player getPlayer(){
        return this.player;
    }
    public Board getBoard() {
        return board;
    }
    public Tile getSelectedTile(){
        return this.selectedTile;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
