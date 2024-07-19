package interface_adapter.remove_piece;

import entity.Board;
import entity.Play;
import entity.Tile;
import use_case.remove_letter.RemoveLetterInputBoundary;
import use_case.remove_letter.RemoveLetterInputData;

public class RemoveLetterController {
    RemoveLetterInputBoundary interactor;
    public RemoveLetterController(RemoveLetterInputBoundary interactor){
        this.interactor = interactor;
    }

    public void execute(int x, int y, Play play, Tile selectedTile, Board board){
        RemoveLetterInputData removeLetterInputData = new RemoveLetterInputData(play, board, selectedTile, x, y);
        interactor.execute(removeLetterInputData);
    }
}
