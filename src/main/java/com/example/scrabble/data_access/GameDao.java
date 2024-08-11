package com.example.scrabble.data_access;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.scrabble.entity.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

/**
 * Represents a data access object for the Game entity.
 * A GameDao can create, read, update, and delete Game objects.
 * Once a Game is created or read, it is loaded into the instance of DAO and can be retrived directly.
 */
@Repository
public class GameDao implements GameDataAccess {

    private static final Logger log = LoggerFactory.getLogger(GameDao.class);
    private static final String bucketName = "scrabble-server-431817_cloudbuild"; // Replace with your bucket name
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Storage storage;


    public GameDao() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    private String makeFilePath(int gameId) {
        return "games/" + gameId + ".json";
    }

    private void writeGame(Game game) {
        try {
            String filePath = makeFilePath(game.getId());
            byte[] data = objectMapper.writeValueAsBytes(game);
            BlobId blobId = BlobId.of(bucketName, filePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, data);
        } catch (IOException e) {
            throw new GameDaoException("Error writing game to file", e);
        }
    }

    private boolean gameExists(int gameId) {
        String filePath = makeFilePath(gameId);
        Blob blob = storage.get(BlobId.of(bucketName, filePath));
        return blob != null && blob.exists();
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

    private Game get(int gameId) {
        try {
            String filePath = makeFilePath(gameId);
            Blob blob = storage.get(BlobId.of(bucketName, filePath));
            if (blob == null || !blob.exists()) {
                throw new GameDaoException("Game not found");
            }
            byte[] data = blob.getContent();
            return objectMapper.readValue(data, Game.class);
        } catch (IOException e) {
            throw new GameDaoException("Error reading game from file", e);
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
