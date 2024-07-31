package com.example.scrabble.use_case.contest;

import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.TurnManager;

public class ContestInputData {
    final private int gameId;
    final private int playerId;

    public ContestInputData(int gameId, int playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return playerId;
    }
}
