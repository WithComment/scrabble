package use_case.EndTurn;

import use_case.confirm_play.ConfirmPlayOutputData;
import use_case.get_leaderboard.GetLeaderboardOutputData;

public interface GetEndTurnOutputBoundary {
    void prepareSuccessView(ConfirmPlayOutputData data);

    void prepareFailView(String error);

    void prepareView(GetLeaderboardOutputData outputData);
}
