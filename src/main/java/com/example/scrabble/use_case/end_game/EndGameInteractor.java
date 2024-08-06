package com.example.scrabble.use_case.end_game;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.Player;
import com.example.scrabble.data_access.GameDao;
import com.example.scrabble.use_case.InvalidPlayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EndGameInteractor implements EndGameInputBoundary{
    private final GameDataAccess gameDao;

    @Autowired
    public EndGameInteractor(GameDataAccess gameDao) {this.gameDao = gameDao;}

    @Override
    public EndGameOutputData execute(EndGameInputData endGameInputData) throws InvalidPlayException {
        Game game = gameDao.get(endGameInputData.getGameId());
        List<Player> players = game.getPlayers();

        Map<Player, Integer> unplayedScores = new HashMap<>();
        for (Player player : players) {
            List<Letter> playerInventory = player.getInventory();
            int unplayedScore = 0;
            for (Letter letter : playerInventory) {
                unplayedScore += letter.getPoints();
            }
            unplayedScores.put(player, unplayedScore);
        }

        boolean onePlayerEmptied = false;
        int bonus = 0;
        for (Integer value : unplayedScores.values()) {
            bonus += value;
            if(value == 0){
                onePlayerEmptied = true;
            }
        }

        if(!onePlayerEmptied){
            throw new InvalidPlayException("The game shouldn't have ended yet");
        }

        int highestScore = 0;
        List<Integer> winners = new ArrayList<>();
        for (Player player : players) {
            int playerPoints = player.getScore();
            int playerFinalScore;
            if (unplayedScores.get(player) == 0) {
                playerFinalScore = playerPoints + bonus;
            } else {
                playerFinalScore = playerPoints - unplayedScores.get(player);
            }
            if (playerFinalScore == highestScore) {
                winners.add(player.getId());
            }
            if (playerFinalScore > highestScore) {
                highestScore = playerFinalScore;
                winners.clear();
                winners.add(player.getId());
            }
        }

        // presenter.prepareSuccessView(new EndGameOutputData(winners));
        // EndGameData endGameData = new EndGameData(game);
        try {
            gameDao.create(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new EndGameOutputData(winners);
    }
}
