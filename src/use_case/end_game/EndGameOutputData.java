package use_case.end_game;

import entity.Player;

import java.util.ArrayList;

public class EndGameOutputData {
    private final ArrayList<Integer> winners;

    public EndGameOutputData(ArrayList<Integer> winners) {
        this.winners = winners;
    }

    public ArrayList<Integer> getWinners() {
        return winners;
    }
}
