package use_case.get_winner;

import entity.LeaderboardEntry;
import entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class GetWinnerInteractor implements GetWinnerInputBoundary{
    private final GetWinnerOutputBoundary presenter;

    public GetWinnerInteractor(GetWinnerOutputBoundary presenter){
        this.presenter = presenter;
    }

    @Override
    public void execute(GetWinnerInputData input){
        ArrayList<LeaderboardEntry> leaderboard = new ArrayList<>();
        for (Player player : input.getPlayers()) {
            leaderboard.add(new LeaderboardEntry(player.getId(), player.getScore()));
        }
        presenter.prepareView(new GetWinnerOutputData(Collections.max(leaderboard)));
    }
}
