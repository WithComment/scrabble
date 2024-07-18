package use_case.get_winner;

import entity.LeaderboardEntry;
import entity.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Implements the use case interactor for determining the winner of the game.
 * This class is responsible for identifying the player with the highest score at the end of the game.
 */
public class GetWinnerInteractor implements GetWinnerInputBoundary {
    private final GetWinnerOutputBoundary presenter;

    /**
     * Constructs a GetWinnerInteractor with a specified presenter.
     * @param presenter The presenter to which the winner's data will be passed.
     */
    public GetWinnerInteractor(GetWinnerOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Executes the use case of getting the winner. It retrieves player data,
     * creates leaderboard entries for each player, identifies the player with the highest score,
     * and then passes the winner's data to the presenter.
     * @param input Contains the list of players from which to determine the winner.
     */
    @Override
    public void execute(GetWinnerInputData input) {
        ArrayList<LeaderboardEntry> leaderboard = new ArrayList<>();
        for (Player player : input.getPlayers()) {
            leaderboard.add(new LeaderboardEntry(player.getId(), player.getScore()));
        }
        // Find the player with the highest score and pass their data to the presenter
        presenter.prepareView(new GetWinnerOutputData(Collections.max(leaderboard)));
    }
}