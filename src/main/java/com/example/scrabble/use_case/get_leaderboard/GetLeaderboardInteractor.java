package com.example.scrabble.use_case.get_leaderboard;

import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the use case interactor for retrieving and presenting the game leaderboard.
 * This class is responsible for fetching the leaderboard data, sorting it, and then
 * passing it to the presenter for display.
 */
public class GetLeaderboardInteractor implements GetLeaderboardInputBoundary {
    private final GetLeaderboardOutputBoundary presenter;
    private final Game game;

    /**
     * Constructs a GetLeaderboardInteractor with a specified presenter.
     * @param presenter The presenter to which the sorted leaderboard will be passed.
     * @param game The game.
     */
    public GetLeaderboardInteractor(GetLeaderboardOutputBoundary presenter, Game game) {
        this.presenter = presenter;
        this.game = game;
    }

    /**
     * Executes the use case of getting the leaderboard. It retrieves player data,
     * creates leaderboard entries for each player, sorts these entries in descending
     * order of scores, and then passes the sorted list to the presenter.
     * @param input Contains the list of players from which to generate the leaderboard.
     */
    @Override
    public void execute(GetLeaderboardInputData input) {
        List<Integer> players = input.getPlayers();
        List<Player> leaderboard = new ArrayList<>();
        for (int playerId : players) {
            Player player = game.getPlayer(playerId);
            leaderboard.add(player);
        }
        Collections.sort(leaderboard, Collections.reverseOrder());
        presenter.prepareView(new GetLeaderboardOutputData(leaderboard));
    }
}