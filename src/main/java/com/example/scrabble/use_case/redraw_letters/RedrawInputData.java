package com.example.scrabble.use_case.redraw_letters;

import java.util.List;

public class RedrawInputData {
    private int gameId;
    private List<String> characters;

    public RedrawInputData() {}

    public RedrawInputData(int gameId, List<String> letters) {
        this.gameId = gameId;
        this.characters = letters;
    }

    public int getGameId() {
        return gameId;
    }

    public List<String> getCharacters() {
        return characters;
    }

}
