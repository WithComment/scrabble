package use_case.end_game;

import entity.Game;
import entity.Letter;
import entity.Player;
import data_access.EndGameDataAccessObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EndGameInteractor implements EndGameInputBoundary{
    final EndGameOutputBoundary presenter;

    public EndGameInteractor(EndGameOutputBoundary endGameOutputBoundary) {
        this.presenter = endGameOutputBoundary;
    }

    @Override
    public void execute(EndGameInputData endGameInputData) throws Exception {
        Game game = endGameInputData.getGame();
        ArrayList<Player> players = endGameInputData.getPlayers();

        Map<Player, Integer> unplayedScores = new HashMap<>();
        for (Player player : players) {
            ArrayList<Letter> playerInventory = player.getInventory();
            int unplayedScore = 0;
            for (Letter letter : playerInventory) {
                unplayedScore += letter.getPoints();
            }
            unplayedScores.put(player, unplayedScore);
        }

        int bonus = 0;
        for (Integer value : unplayedScores.values()) {
            bonus += value;
        }

        int highestScore = 0;
        Player winner = null;
        for (Player player : players) {
            int playerPoints = player.getScore();
            int playerFinalScore;
            if(unplayedScores.get(player) == 0){
                playerFinalScore = playerPoints + bonus;
            }else{
                playerFinalScore = playerPoints - unplayedScores.get(player);
            }

            if (playerFinalScore > highestScore) {
                highestScore = playerFinalScore;
                winner = player;
            }

            presenter.prepareView(new EndGameOutputData(winner));
            EndGameData endGameData = new EndGameData(game);
            EndGameDataAccessObject endGameDataAccessObject = new EndGameDataAccessObject();
            endGameDataAccessObject.write(endGameData);

        }
    }
}
