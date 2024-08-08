package com.example.scrabble.use_case.end_game;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.InvalidPlayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handle the end game use case.
 */
@Service
public class EndGameInteractor implements EndGameInputBoundary {
    private final GameDataAccess gameDao;

    /**
     * Constructs an EndGameInteractor instance with the specified GameDataAccess.
     *
     * @param gameDao the data access object for game data
     */
    @Autowired
    public EndGameInteractor(GameDataAccess gameDao) {
        this.gameDao = gameDao;
    }

    /**
     * Calculates the scores of unplayed letters for each player.
     *
     * @param players the list of players
     * @return a map of players to their unplayed scores
     */
    private Map<Player, Integer> calculateUnplayedScores(List<Player> players) {
        Map<Player, Integer> unplayedScores = new HashMap<>();
        for (Player player : players) {
            List<Letter> playerInventory = player.getInventory();
            int unplayedScore = 0;
            for (Letter letter : playerInventory) {
                unplayedScore += letter.getPoints();
            }
            unplayedScores.put(player, unplayedScore);
        }
        return unplayedScores;
    }

    /**
     * Calculates the bonus based on unplayed scores.
     *
     * @param unplayedScores the map of players to their unplayed scores
     * @return the calculated bonus
     * @throws InvalidPlayException if no player has emptied their rack
     */
    private int calculateBonus(Map<Player, Integer> unplayedScores) {
        boolean onePlayerEmptied = false;
        int bonus = 0;
        for (Integer value : unplayedScores.values()) {
            bonus += value;
            if (value == 0) {
                onePlayerEmptied = true;
            }
        }
        if (!onePlayerEmptied) {
            throw new InvalidPlayException("The game shouldn't have ended yet");
        }
        return bonus;
    }

    /**
     * Determines the winners based on final scores.
     *
     * @param unplayedScores the map of players to their unplayed scores
     * @param bonus the calculated bonus
     * @return the list of winner IDs
     */
    private List<Integer> getWinners(Map<Player, Integer> unplayedScores, int bonus) {
        int highestScore = 0;
        List<Integer> winners = new ArrayList<>();
        for (Player player : unplayedScores.keySet()) {
            if (unplayedScores.get(player) == 0) {
                player.setTempScore(bonus);
                player.confirmTempScore();
            } else {
                player.setTempScore(-1 * unplayedScores.get(player));
                player.confirmTempScore();
            }

            int playerFinalScore = player.getScore();

            if (playerFinalScore == highestScore) {
                winners.add(player.getId());
            }
            if (playerFinalScore > highestScore) {
                highestScore = playerFinalScore;
                winners.clear();
                winners.add(player.getId());
            }
        }
        return winners;
    }

    /**
     * Executes the end game use case.
     *
     * @param endGameInputData the input data for ending the game
     * @return the output data containing the list of winner IDs
     * @throws InvalidPlayException if the game should not have ended yet
     */
    @Override
    public EndGameOutputData execute(EndGameInputData endGameInputData) throws InvalidPlayException {
        Game game = gameDao.get(endGameInputData.getGameId());
        List<Player> players = game.getPlayers();

        Map<Player, Integer> unplayedScores = calculateUnplayedScores(players); // calculate the total scores of each player's unplayed letters

        int bonus = calculateBonus(unplayedScores); // based on the score of the unplayed letters, calculate the bonus, which will be added to the score of the player who emptied their rack

        List<Integer> winners = getWinners(unplayedScores, bonus); // calculate the final scores for each player, and find the winners

        return new EndGameOutputData(winners);
    }
}