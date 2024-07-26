package use_case.end_game;

import entity.Game;
import entity.Letter;
import entity.Player;
import data_access.GameDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndGameInteractor implements EndGameInputBoundary{
    final EndGameOutputBoundary presenter;

    public EndGameInteractor(EndGameOutputBoundary endGameOutputBoundary) {
        this.presenter = endGameOutputBoundary;
    }

    @Override
    public void execute(EndGameInputData endGameInputData){
        Game game = endGameInputData.getGame();
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
            presenter.prepareFailView("The game should not be over yet");
            return;
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

        presenter.prepareSuccessView(new EndGameOutputData(winners));
        EndGameData endGameData = new EndGameData(game);
        GameDao endGameDataAccessObject = new GameDao();
        try {
            endGameDataAccessObject.create(endGameData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
