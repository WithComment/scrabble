package use_case.EndTurn;
import entity.Game;
import entity.LetterBag;
import entity.Player;

import java.util.ArrayList;


public class GetEndTurnOutPutData {
    final ArrayList<Player> players;
    boolean isContestSucceed;
    boolean isContest;
    LetterBag letterBag;
    Game game;

    public GetEndTurnOutPutData(ArrayList<Player> players, boolean isContestSucceed, boolean isContest, LetterBag letterBag, Game game) {
        this.players = players;
        this.isContestSucceed = isContestSucceed;
        this.isContest = isContest;
        this.letterBag = letterBag;
        this.game = game;

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public LetterBag getLetterBag() {
        return letterBag;
    }
}
