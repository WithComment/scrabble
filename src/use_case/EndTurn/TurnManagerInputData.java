package use_case.EndTurn;
import entity.LetterBag;
import entity.Player;

import java.util.ArrayList;


public class TurnManagerInputData {
        final ArrayList<Player> players;
        boolean isContestSucceed;
        boolean isContest;
        LetterBag letterBag;

    public TurnManagerInputData(ArrayList<Player> players, boolean isContestSucceed, boolean isContest, LetterBag letterBag) {
        this.players = players;
        this.isContestSucceed = isContestSucceed;
        this.isContest = isContest;
        this.letterBag = letterBag;

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public LetterBag getLetterBag() {
        return letterBag;
    }
}
