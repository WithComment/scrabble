package use_case.EndTurn;
import entity.Player;

import java.util.ArrayList;


public class TurnManagerInputData {
        final ArrayList<Player> players;
        boolean isContestSucceed;
        boolean isContest;

    public TurnManagerInputData(ArrayList<Player> players, boolean isContestSucceed, boolean isContest) {
        this.players = players;
        this.isContestSucceed = isContestSucceed;
        this.isContest = isContest;

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
