package com.example.scrabble.use_case.get_leaderboard;

import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.LeaderboardEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.scrabble.data_access.GameDataAccess;

/**
 * Implements the use case interactor for retrieving and presenting the game leaderboard.
 * This class is responsible for fetching the leaderboard data, sorting it, and then
 * passing it to the presenter for display.
 */
@Service
public class GetLeaderboardInteractor implements GetLeaderboardInputBoundary {
    private final GameDataAccess gameDao;

    /**
     * Constructs a GetLeaderboardInteractor with a specified presenter.
     * @param gameDao The data access object for retrieving game data.
     */
    @Autowired
    public GetLeaderboardInteractor(GameDataAccess gameDao) {
//        this.presenter = presenter;
        this.gameDao = gameDao;
    }

    /**
     * Executes the use case of getting the leaderboard. It retrieves player data,
     * creates leaderboard entries for each player, sorts these entries in descending
     * order of scores, and then passes the sorted list to the presenter.
     *
     * @param input Contains the list of players from which to generate the leaderboard.
     */
    @Override
    public GetLeaderboardOutputData execute(GetLeaderboardInputData input) {
        Game game = gameDao.get(input.getGameId());
        List<Player> players = game.getPlayers();
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        for (Player player : players) {
            int score = player.getScore(); // Assume getScore() method exists in Player
            LeaderboardEntry entry = new LeaderboardEntry(player, score);
            leaderboard.add(entry);
        }
        Collections.sort(leaderboard, Collections.reverseOrder());
        // Extract sorted players from leaderboardEntries
        List<Player> sortedPlayers = new ArrayList<>();
        for (LeaderboardEntry entry : leaderboard) {
            sortedPlayers.add(entry.getPlayer());
        }

        // Set the sorted players as the leaderboard
        game.setLeaderboard(sortedPlayers);
        gameDao.update(game);

        return new GetLeaderboardOutputData(sortedPlayers);
    }
}