package use_case.EndTurn;
import entity.Player;

import java.util.ArrayList;


public class TurnManagerInputData {
        final ArrayList<Player> players;
        boolean isContestSucceed;

    public TurnManagerInputData(ArrayList<Player> players, boolean isContestSucceed) {
        this.players = players;
        this.isContestSucceed = isContestSucceed;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
