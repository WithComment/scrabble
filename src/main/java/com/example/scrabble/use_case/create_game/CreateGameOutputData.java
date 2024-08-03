package com.example.scrabble.use_case.create_game;

import com.example.scrabble.entity.Player;

import java.util.List;

public class CreateGameOutputData {
    private final List<Player> players;
    private int gameId;

    public CreateGameOutputData(List<Player> players, int gameId) {
        this.players = players;
        this.gameId = gameId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
