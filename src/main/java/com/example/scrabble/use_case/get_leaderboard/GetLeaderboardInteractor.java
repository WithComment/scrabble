package com.example.scrabble.use_case.get_leaderboard;

import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
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
        List<Integer> players = input.getPlayers();
        List<Player> leaderboard = new ArrayList<>();
        for (int playerId : players) {
            Player player = game.getPlayer(playerId);
            leaderboard.add(player);
        }
        Collections.sort(leaderboard, Collections.reverseOrder());
        game.setLeaderboard(leaderboard);
        return new GetLeaderboardOutputData(leaderboard);
    }
}