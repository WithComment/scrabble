package use_case.remove_letter;

import entity.Board;
import entity.Play;
import entity.Player;
import entity.Tile;
import entity.Move;
import entity.Letter;

public class RemoveLetterInteractor implements RemoveLetterInputBoundary{
    private Play play;
    private Player player;
    private Board board;
    private Tile selectedTile;
    public RemoveLetterInteractor() {
    }

    public void execute(RemoveLetterInputData removeLetterInputData){
        Play play = removeLetterInputData.getPlay();
        Player player = play.getPlayer();
        Board board = removeLetterInputData.getBoard();
        Tile selectedTile = removeLetterInputData.getSelectedTile();
        int x = removeLetterInputData.getX();
        int y = removeLetterInputData.getY();
        boolean isValidClick = false;
        for (Move move : play.getMoves()){
            if (move.getLetter() == selectedTile.getLetter()
                    && move.getX() == x
                    && move.getY() == y){
                isValidClick = true;
            }
        }
        if (!isValidClick){
            //Failure!
            return; // TODO
        } else{
            play.removeMove(x, y);
            selectedTile.removeLetter();
        }
    }
}
