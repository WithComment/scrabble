package data_access;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import entity.Game;

/**
 * Represents a data access object for the Game entity.
 * A GameDao can create, read, update, and delete Game objects.
 * Once a Game is created or read, it is loaded into the instance of DAO and can be retrived directly.
 */
public class GameDao implements GameDataAccess {
    private Game game;

    private String makeFilePath(int gameId) {
        return "../database/" + gameId + ".txt";
    }

    private void writeGame(Game game) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(makeFilePath(game.getId()));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(game);
        objectOutputStream.close();
        fileOutputStream.close();
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
    public Game create(Game game) throws FileNotFoundException, IOException, IllegalArgumentException {
        if (gameExists(game.getId())) {
            throw new IllegalArgumentException("Game with the same ID already exists.");
        }
        writeGame(game);
        this.game = game;
        return game;
    }

    /**
     * Load a specific Game from a file based on gameId.
     * If there the of DAO has a Game with the same gameId, return the Game in memory.
     * @param gameId the ID of the Game to be loaded.
     * @return the Game object.
     */
    public Game read(int gameId) throws FileNotFoundException, IOException, ClassNotFoundException {
        if (this.game != null && this.game.getId() == gameId) {
            return this.game;
        }
        if (!gameExists(gameId)) {
            throw new IllegalArgumentException("Game with the specified ID does not exist.");
        }
        FileInputStream fileInputStream = new FileInputStream(makeFilePath(gameId));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Game game = (Game) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        this.game = game;
        return game;
    }
    
    public Game getGame() {
        return this.game;
    }

    /**
     * Update the Game in a file based on gameId.
     * @param game the Game object to be updated.
     */
    public void update(Game game) throws FileNotFoundException, IOException {
        writeGame(game);
        this.game = game;
    }

    /**
     * Delete the Game in a file based on gameId.
     * @param gameId the ID of the Game to be deleted.
     */
    public void delete(int gameId) throws FileNotFoundException, IOException {
        new File(makeFilePath(gameId)).delete();
        if (this.game != null && this.game.getId() == gameId) {
            this.game = null;
        }
    }
}
