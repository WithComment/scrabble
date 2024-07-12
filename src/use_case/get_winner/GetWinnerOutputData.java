package use_case.get_winner;

import entity.LeaderboardEntry;

public class GetWinnerOutputData {
    private final LeaderboardEntry winner;

    public GetWinnerOutputData(LeaderboardEntry winner) {
        this.winner = winner;
    }

    public LeaderboardEntry getWinner() {
        return winner;
    }
}
