package use_case.get_leaderboard;

import entity.LeaderboardEntry;
import entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class GetLeaderboardInteractor implements GetLeaderboardInputBoundary{
    private final GetLeaderboardOutputBoundary presenter;

    public GetLeaderboardInteractor(GetLeaderboardOutputBoundary presenter){
        this.presenter = presenter;
    }

    @Override
    public void execute(GetLeaderboardInputData input){
        ArrayList<LeaderboardEntry> leaderboard = new ArrayList<>();
        for (Player player : input.getPlayers()) {
            leaderboard.add(new LeaderboardEntry(player.getId(), player.getScore()));
        }
        Collections.sort(leaderboard, Collections.reverseOrder());
        presenter.prepareView(new GetLeaderboardOutputData(leaderboard));
    }
}
