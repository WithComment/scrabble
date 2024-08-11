package com.example.scrabble.data_access;

import com.example.scrabble.entity.Game;

/**
 * Interface for data access operations related to the Game entity.
 */
public interface GameDataAccess {
    /**
     * Exception class for handling data access related exceptions.
     */
    class GameDaoException extends RuntimeException {
        /**
         * Constructs a new GameDaoException with the specified detail message and cause.
         *
         * @param message the detail message.
         * @param cause the cause of the exception.
         */
        public GameDaoException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Creates a new game in the data store.
     *
     * @param game the game to create.
     * @return the created game.
     */
    Game create(Game game);

    /**
     * Retrieves a game from the data store by its ID.
     *
     * @param gameId the ID of the game to retrieve.
     * @return the retrieved game.
     */
    Game get(int gameId);

    /**
     * Updates an existing game in the data store.
     *
     * @param game the game to update.
     */
    void update(Game game);

    /**
     * Deletes a game from the data store by its ID.
     *
     * @param gameId the ID of the game to delete.
     */
    void delete(int gameId);
}