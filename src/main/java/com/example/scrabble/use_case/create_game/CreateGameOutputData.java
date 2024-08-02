package com.example.scrabble.use_case.create_game;

import com.example.scrabble.entity.Player;

import java.util.List;

public class CreateGameOutputData {
    private final List<Player> players;

    public CreateGameOutputData(List<Player> players) {
        this.players = players;
    }

    public List<Player> getGameId() {
        return players;
    }
}
