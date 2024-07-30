package com.example.scrabble.use_case.redraw_letters;

import java.util.ArrayList;
import java.util.List;

public class RedrawInputData {
    private final int gameId;
    private final List<Character> characters;

    public RedrawInputData(){
        this.gameId = 0;
        this.characters = new ArrayList<>();
    }

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
