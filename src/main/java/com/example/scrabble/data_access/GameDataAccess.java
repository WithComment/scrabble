package com.example.scrabble.data_access;

import com.example.scrabble.entity.Game;

public interface GameDataAccess {
    class GameDaoException extends RuntimeException {
        public GameDaoException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Game create(Game game);
    Game get(int gameId);
    void update(Game game);
    void delete(int gameId);
}
