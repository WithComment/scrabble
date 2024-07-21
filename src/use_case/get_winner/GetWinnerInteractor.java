package use_case.get_winner;

import entity.LeaderboardEntry;
import entity.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Implements the use case interactor for determining the winner(s) of the game.
 * This class is responsible for identifying the player(s) with the highest score at the end of the game.
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
     * Executes the use case of getting the winner(s). It retrieves player data,
     * creates leaderboard entries for each player, sorts them to identify the player(s) with the highest score,
     * and then passes the winner(s)' data to the presenter.
     * In case of a tie, all top-scoring players are considered winners.
     * @param input Contains the list of players from which to determine the winner(s).
     */
    @Override
    public void execute(GetWinnerInputData input) {
        ArrayList<LeaderboardEntry> leaderboard = new ArrayList<>();
        for (Player player : input.getPlayers()) {
            leaderboard.add(new LeaderboardEntry(player, player.getScore()));
        }
        ArrayList<LeaderboardEntry> winner = new ArrayList<>();
        Collections.sort(leaderboard, Collections.reverseOrder());
        winner.add(Collections.max(leaderboard));
        int i = 1;
        // Check for ties by comparing scores of subsequent players in the sorted list
        while (i < leaderboard.size() && leaderboard.get(i).getScore() == winner.get(0).getScore()) {
            winner.add(leaderboard.get(i));
            i++;
        }
        presenter.prepareView(new GetWinnerOutputData(winner));
    }
}