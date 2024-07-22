package use_case.get_leaderboard;

import entity.Player;
import java.util.List;

public class GetLeaderboardInputData {
    final List<Player> players;

    public GetLeaderboardInputData(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
