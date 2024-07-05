package entity;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class TurnManager {
    private Boolean endTurn;
    private Player CurrentPlayer;
    private int PlayerNumber;
    private ArrayList<Player> Players;
    private ArrayList<Integer> NumContestFailed;

    public TurnManager() {
        this.endTurn = false;
        this.CurrentPlayer = null;
        this.Players = new ArrayList<>();
        this.NumContestFailed = new ArrayList<>();
    }

    public void EndTurn() {
        endTurn = true;
    }

    public void CheckAndEndTurn() {
        while(NumContestFailed.get((PlayerNumber + 1) % Players.size()) > 0) {
            int NumContestFailedOfNextPlayer = NumContestFailed.get((PlayerNumber + 1) % Players.size());
            NumContestFailed.set((PlayerNumber + 1) % Players.size(), NumContestFailedOfNextPlayer-1);
            PlayerNumber = (PlayerNumber + 1) % Players.size();
        }
        PlayerNumber = (PlayerNumber + 1) % Players.size();
        // Notify the front-end or other players that the turn has ended and it's the next player's turn
        CurrentPlayer = Players.get(PlayerNumber);
        System.out.println("It's now player " + PlayerNumber + "'s turn.");
    }

    public void ContestFailureUodate(int PlayerNumber) {
        int CurrentFailure = NumContestFailed.get(PlayerNumber);
        NumContestFailed.set(PlayerNumber, CurrentFailure + 1);
    }

    public Player ReturnCurrentPlayer() {
        CurrentPlayer = Players.get(PlayerNumber);
        return CurrentPlayer;
    }


}






