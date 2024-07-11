package use_case.get_leaderboard;

import entity.LeaderboardEntry;

import java.util.ArrayList;

public class GetLeaderboardOutputData {
    private final ArrayList<LeaderboardEntry> leaderboard;

    public GetLeaderboardOutputData(ArrayList<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public ArrayList<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }
}
