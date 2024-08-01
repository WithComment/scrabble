package com.example.scrabble.use_case.start_turn;

import com.example.scrabble.entity.Play;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.Letter;

import java.util.ArrayList;
import java.util.List;

public class StartTurnOutputData {
    private final Player player;
    private final Play play;

    public StartTurnOutputData(Player player, Play play) {
        this.player = player;
        this.play = play;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerID() {
        return player.getId();
    }

    public List<Character> getHandCharacters() {
        ArrayList<Character> handChars = new ArrayList<>();
        for (Letter letter : player.getInventory()) {
            handChars.add(letter.getLetter());
        }
        return handChars;
    }

    public Play getPlay() {
        return play;
    }
}