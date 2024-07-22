package use_case.get_leaderboard;

import entity.LeaderboardEntry;
import entity.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Implements the use case interactor for retrieving and presenting the game leaderboard.
 * This class is responsible for fetching the leaderboard data, sorting it, and then
 * passing it to the presenter for display.
 */
public class GetLeaderboardInteractor implements GetLeaderboardInputBoundary {
    private final GetLeaderboardOutputBoundary presenter;

    /**
     * Constructs a GetLeaderboardInteractor with a specified presenter.
     * @param presenter The presenter to which the sorted leaderboard will be passed.
     */
    public GetLeaderboardInteractor(GetLeaderboardOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Executes the use case of getting the leaderboard. It retrieves player data,
     * creates leaderboard entries for each player, sorts these entries in descending
     * order of scores, and then passes the sorted list to the presenter.
     * @param input Contains the list of players from which to generate the leaderboard.
     */
    @Override
    public void execute(GetLeaderboardInputData input) {
        ArrayList<LeaderboardEntry> leaderboard = new ArrayList<>();
        for (Player player : input.getPlayers()) {
            leaderboard.add(new LeaderboardEntry(player, player.getScore()));
        }
        System.out.println(input.getPlayers().size());
        Collections.sort(leaderboard, Collections.reverseOrder());
        presenter.prepareView(new GetLeaderboardOutputData(leaderboard));
    }
}