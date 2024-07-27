package interface_adapter.end_turn;

import interface_adapter.GameViewModel;
import use_case.EndTurn.GetEndTurnOutputBoundary;
import use_case.confirm_play.ConfirmPlayOutputData;
import use_case.get_leaderboard.GetLeaderboardOutputData;

public class EndTurnPresenter implements GetEndTurnOutputBoundary {
    private GameViewModel gameViewModel;

    public EndTurnPresenter(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
    }

    public GameViewModel getGameViewModel() {
        return gameViewModel;
    }

    @Override
    public void prepareSuccessView(ConfirmPlayOutputData data) {
    }

    @Override
    public void prepareFailView(String error) {
    }

    @Override
    public void prepareView(GetLeaderboardOutputData outputData) {

    }
}

