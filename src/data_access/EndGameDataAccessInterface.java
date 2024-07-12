package data_access;

import entity.Game;
import use_case.end_game.EndGameData;

public interface EndGameDataAccessInterface {
    public void write(EndGameData endGameData) throws Exception;
}
