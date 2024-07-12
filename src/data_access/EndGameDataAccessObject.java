package data_access;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import entity.Game;
import entity.Board;

public class EndGameDataAccessObject implements EndGameDataAccessInterface{
    public void write(EndGameData endGameData) throws Exception{
        String filePath = "../database/" + endGameData.game.id + ".txt";
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ObjectOutputStream.write(endGameData.game);
    }
}
