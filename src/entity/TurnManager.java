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
            PlayerNumber = PlayerNumber + 1;
            if(PlayerNumber == Players.size()) {
                PlayerNumber = 0;
            }
            CurrentPlayer = Players.get(PlayerNumber);
            endTurn = false;
        }
    }

    public Player ReturnCurrentPlayer() {
        CurrentPlayer = Players.get(PlayerNumber);
        return CurrentPlayer;
    }
}
