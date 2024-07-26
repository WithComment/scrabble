package use_case.EndTurn;
import entity.Game;
import entity.LetterBag;
import entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GetEndTurnInputData {
        final ArrayList<Player> players;
        boolean isContestSucceed;
        boolean isContest;
        LetterBag letterBag;
        Game game;
        List<List<Integer>> wordsToBeConfirmed;

    public GetEndTurnInputData(ArrayList<Player> players, boolean isContestSucceed, boolean isContest, LetterBag letterBag,
                               Game game, List<List<Integer>> wordsToBeConfirmed) {
        this.players = players;
        this.isContestSucceed = isContestSucceed;
        this.isContest = isContest;
        this.letterBag = letterBag;
        this.game = game;
        this.wordsToBeConfirmed = wordsToBeConfirmed;

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public LetterBag getLetterBag() {
        return letterBag;
    }
}
