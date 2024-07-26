package data_access;

import java.io.FileNotFoundException;
import java.io.IOException;

import entity.Game;

public interface GameDataAccess {
    public Game create(Game game) throws FileNotFoundException, IOException;
    public Game read(int gameId) throws FileNotFoundException, IOException, ClassNotFoundException;
    public Game getGame();
    public void update(Game game) throws FileNotFoundException, IOException;
    public void delete(int gameId) throws FileNotFoundException, IOException;
}
