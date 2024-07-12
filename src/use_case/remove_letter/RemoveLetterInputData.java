package use_case.remove_letter;

import entity.Board;
import entity.Play;
import entity.Player;
import entity.Tile;

public class RemoveLetterInputData {
    private int x;
    private int y;
    private Play play;
    private Board board;
    private Tile selectedTile;
    public RemoveLetterInputData(
            Play play,
            Board board,
            Tile selectedTile,
            int x,
            int y
    ) {
        this.play = play;
        this.board = board;
        this.selectedTile = selectedTile;
        this.x = x;
        this.y = y;
    }

    public Play getPlay(){
        return this.play;
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
