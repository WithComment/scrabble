package use_case.get_leaderboard;

import entity.Player;

import java.util.List;

public class GetLeaderboardOutputData {
    private final List<Player> leaderboard;

    public GetLeaderboardOutputData(List<Player> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public List<Player> getLeaderboard() {
        return leaderboard;
    }
}
