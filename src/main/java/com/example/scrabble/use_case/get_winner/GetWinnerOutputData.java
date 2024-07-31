package use_case.get_winner;

import com.example.scrabble.entity.LeaderboardEntry;

import java.util.ArrayList;

public class GetWinnerOutputData {
    private final ArrayList<LeaderboardEntry> winner;

    public GetWinnerOutputData(ArrayList<LeaderboardEntry> winner) {
        this.winner = winner;
    }

    public ArrayList<LeaderboardEntry> getWinner() {
        return winner;
    }
}
