package use_case.get_leaderboard;

import entity.Player;
import java.util.ArrayList;

public class GetLeaderboardInputData {
    final ArrayList<Player> players;

    public GetLeaderboardInputData(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
