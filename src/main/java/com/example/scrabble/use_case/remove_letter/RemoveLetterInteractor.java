package com.example.scrabble.use_case.remove_letter;

import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Play;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.Tile;
import com.example.scrabble.entity.Move;
import com.example.scrabble.entity.Letter;

public class RemoveLetterInteractor implements RemoveLetterInputBoundary{
    private Play play;
    private Player player;
    private Board board;
    private Tile selectedTile;
    private RemoveLetterOutputBoundary presenter;
    public RemoveLetterInteractor(RemoveLetterOutputBoundary presenter) {
        this.presenter = presenter;
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
                break;
            }
        }
        if (!isValidClick){
            presenter.prepareFailureView(new RemoveLetterOutputData(false, board, player.getInventory()));
        } else{
            play.removeMove(x, y);
            player.addLetter(selectedTile.getLetter());
            selectedTile.removeLetter();
            presenter.prepareSuccessView(new RemoveLetterOutputData(true, board, player.getInventory()));
        }
    }
}
