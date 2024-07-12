package entity;

import java.util.ArrayList;

public class TurnManager {
    private Boolean endTurn;
    private Player CurrentPlayer;
    private int PlayerNumber;
    private final ArrayList<Player> Players;
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

    public void startTurn() {
        endTurn = false;
    }

    public void CheckAndEndTurn() {
        Player currentPlayer = ReturnCurrentPlayer();
        currentPlayer.NotContested();
        while (NumContestFailed.get((PlayerNumber + 1) % Players.size()) > 0) {
            int NumContestFailedOfNextPlayer = NumContestFailed.get((PlayerNumber + 1) % Players.size());
            NumContestFailed.set((PlayerNumber + 1) % Players.size(), NumContestFailedOfNextPlayer - 1);
            PlayerNumber = (PlayerNumber + 1) % Players.size();
        }
        PlayerNumber = (PlayerNumber + 1) % Players.size();
        // Notify the front-end or other players that the turn has ended and it's the next player's turn
        CurrentPlayer = Players.get(PlayerNumber);
        System.out.println("It's now player " + PlayerNumber + "'s turn.");
    }

    public void ContestFailureUpdate(int PlayerNumber) {
        int CurrentFailure = NumContestFailed.get(PlayerNumber);
        NumContestFailed.set(PlayerNumber, CurrentFailure + 1);
        Player currentPlayer = ReturnCurrentPlayer();
        currentPlayer.BeContested();
    }

    public Player ReturnCurrentPlayer() {
        CurrentPlayer = Players.get(PlayerNumber);
        return CurrentPlayer;
    }

    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed) {
            NumContestFailed.add(PlayerNumber);
        }
    }


}
