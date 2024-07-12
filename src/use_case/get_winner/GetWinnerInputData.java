package use_case.get_winner;

import entity.Player;

import java.util.ArrayList;

public class GetWinnerInputData {
    final ArrayList<Player> players;

    public GetWinnerInputData(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
