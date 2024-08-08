package com.example.scrabble.data_access;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.example.scrabble.entity.Game;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a data access object for the Game entity.
 * A GameDao can create, read, update, and delete Game objects.
 * Once a Game is created or read, it is loaded into the instance of DAO and can be retrived directly.
 */
@Repository
public class GameDao implements GameDataAccess {

    private static final Logger log = LoggerFactory.getLogger(GameDao.class);
    private static final String directoryPath = "database";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Create a GameDao object.
     * If the directory for storing Game files does not exist, create it.
     */
    public GameDao() {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private String makeFilePath(int gameId) {
        return directoryPath + "/" + gameId + ".json";
    }

    private void writeGame(Game game) {
        try {
            if (!gameExists(game.getId())) {
                new File(makeFilePath(game.getId())).createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(makeFilePath(game.getId()));
            objectMapper.writeValue(fileOutputStream, game);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new GameDaoException("Error writing game to file", e);
        }
    }

    private boolean gameExists(int gameId) {
        return new File(makeFilePath(gameId)).isFile();
    }

    /**
     * Save the Game to a file.
     * Load the new Game object into the instance of DAO.
     * @param game the Game object to be saved.
     * @return the created Game object.
     */
    public Game create(Game game) {
        if (gameExists(game.getId())) {
            update(game);
            return game;
        }
        writeGame(game);
        return game;
    }

    /**
     * Load a specific Game from a file based on gameId.
     * If there the of DAO has a Game with the same gameId, return the Game in memory.
     * @param gameId the ID of the Game to be loaded.
     * @return the Game object.
     */
    @Override
    @Cacheable(value = "game", key = "#gameId")
    public Game get(int gameId) {
        log.debug("Loading game " + gameId);
        if (!gameExists(gameId)) {
            throw new IllegalArgumentException("Game with the specified ID does not exist.");
        }
        try {
            return objectMapper.readValue(new File(makeFilePath(gameId)), Game.class);
        } catch (Exception e) {
            throw new GameDaoException("Error loading game from file", e);
        }
    }

    /**
     * Update the Game in a file based on gameId.
     * @param game the Game object to be updated.
     */
    public void update(Game game) {
        writeGame(game);
    }

    /**
     * Delete the Game in a file based on gameId.
     * @param gameId the ID of the Game to be deleted.
     */
    public void delete(int gameId) {
        new File(makeFilePath(gameId)).delete();
    }
}
