package com.example.scrabble.use_case.remove_letter;

import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Play;

public class RemoveLetterInputData {
    private final int x;
    private final int y;
    private final Play play;
    private final Board board;
    public RemoveLetterInputData(
            Play play,
            Board board,
            int x,
            int y
    ) {
        this.play = play;
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public Play getPlay(){
        return this.play;
    }
    public Board getBoard() {
        return board;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
