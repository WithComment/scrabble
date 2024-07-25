package use_case.EndTurn;

import use_case.get_leaderboard.GetLeaderboardInputData;

public interface GetEndTurnInputBoundary {


    void execute(TurnManagerInputData data);
}
