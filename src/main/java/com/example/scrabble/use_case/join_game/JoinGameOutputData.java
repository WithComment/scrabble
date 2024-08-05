package com.example.scrabble.use_case.join_game;

public class JoinGameOutputData {
    private final String playerName;

    public JoinGameOutputData(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}