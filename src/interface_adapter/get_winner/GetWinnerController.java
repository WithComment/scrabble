package interface_adapter.get_winner;

import entity.Player;
import use_case.get_winner.*;

import java.util.ArrayList;

public class GetWinnerController {
    final GetWinnerInputBoundary getWinnerUseCaseInteractor;

    public GetWinnerController(GetWinnerInputBoundary getLeaderboardUseCaseInteractor) {
        this.getWinnerUseCaseInteractor = getLeaderboardUseCaseInteractor;
    }

    public void execute(ArrayList<Player> players) {
        GetWinnerInputData getLeaderboardInputData = new GetWinnerInputData(players);
        getWinnerUseCaseInteractor.execute(getLeaderboardInputData);
    }
}
