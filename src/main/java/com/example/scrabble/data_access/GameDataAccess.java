package com.example.scrabble.data_access;

import com.example.scrabble.entity.Game;

public interface GameDataAccess {
    public class GameDaoException extends RuntimeException {
        public GameDaoException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public Game create(Game game);
    public Game get(int gameId);
    public void update(Game game);
    public void delete(int gameId);
}
