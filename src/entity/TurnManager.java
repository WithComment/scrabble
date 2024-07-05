package entity;

import java.util.ArrayList;

public class TurnManager {
    private Boolean endTurn;
    private Player CurrentPlayer;
    private int PlayerNumber;
    private ArrayList<Player> Players;

    public TurnManager() {
        this.endTurn = false;
        this.CurrentPlayer = null;
        this.Players = new ArrayList<>();
    }

    public void EndTurn() {
        endTurn = true;
    }

    public void CheckAndEndTurn() {
        if (endTurn) {
            CurrentPlayer = Players.get(PlayerNumber + 1);
            PlayerNumber = PlayerNumber + 1;
            endTurn = false;
        }
    }
}
