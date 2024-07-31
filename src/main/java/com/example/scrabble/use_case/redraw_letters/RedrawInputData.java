package com.example.scrabble.use_case.redraw_letters;

import java.util.ArrayList;
import java.util.List;

public class RedrawInputData {
    private int gameId;
    private List<Character> characters;

    public RedrawInputData() {}

    public RedrawInputData(int gameId, List<Character> letters) {
        this.gameId = gameId;
        this.characters = letters;
    }

    public int getGameId() {
        return gameId;
    }

    public List<Character> getCharacters() {
        return characters;
    }

}
