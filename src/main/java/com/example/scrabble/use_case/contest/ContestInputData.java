package com.example.scrabble.use_case.contest;

import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.TurnManager;

public class ContestInputData {
    final private Game game;
    final private Player player;

    public ContestInputData(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }
}
