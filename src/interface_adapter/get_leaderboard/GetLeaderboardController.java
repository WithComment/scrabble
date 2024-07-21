package interface_adapter.get_leaderboard;

import entity.Player;
import use_case.get_leaderboard.*;

import java.util.ArrayList;

public class GetLeaderboardController {

    final GetLeaderboardInputBoundary getLeaderboardUseCaseInteractor;

    public GetLeaderboardController(GetLeaderboardInputBoundary getLeaderboardUseCaseInteractor) {
        this.getLeaderboardUseCaseInteractor = getLeaderboardUseCaseInteractor;
    }

    public void execute(ArrayList<Player> players) {
        GetLeaderboardInputData getLeaderboardInputData = new GetLeaderboardInputData(players);
        getLeaderboardUseCaseInteractor.execute(getLeaderboardInputData);
    }
}
