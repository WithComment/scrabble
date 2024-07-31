package com.example.scrabble.use_case.remove_letter;

public class RemoveLetterInputData {
    private int gameId;
    private int x;
    private int y;

    public RemoveLetterInputData() {}

    public RemoveLetterInputData(
        int gameId,
        int x,
        int y
    ) {
        this.gameId = gameId;
        this.x = x;
        this.y = y;
    }

    public int getGameId() {
      return gameId;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
