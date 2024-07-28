package com.example.scrabble.data_access;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.scrabble.entity.Game;

public interface GameDataAccess {
    public Game create(Game game) throws FileNotFoundException, IOException;
    public Game get(int gameId) throws FileNotFoundException, IOException, ClassNotFoundException;
    public void update(Game game) throws FileNotFoundException, IOException;
    public void delete(int gameId) throws FileNotFoundException, IOException;
}
