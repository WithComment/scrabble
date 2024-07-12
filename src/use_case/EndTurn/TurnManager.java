package use_case.EndTurn;

import entity.Player;

import java.util.ArrayList;
import java.util.List;


public class TurnManager implements EndTurn, DealingContest, StartTurn {
    private Boolean endTurn;
    private Player CurrentPlayer;
    private int PlayerNumber;
    private final ArrayList<Player> Players;
    private ArrayList<Integer> NumContestFailed;

    public TurnManager(List<Player> players, ArrayList<Player> players1) {
        this.endTurn = false;
        this.CurrentPlayer = null;
        this.Players = new ArrayList<>();
        this.NumContestFailed = new ArrayList<>();
    }

    public Player getCurrentPlayer() {
        this.CurrentPlayer = Players.get(this.PlayerNumber);
        return this.CurrentPlayer;
    }

    @Override
    public void endTurn() {
        this.endTurn = true;
        // Additional game state updates can be added here
    }

    @Override
    public void startTurn(){

        while (NumContestFailed.get((PlayerNumber + 1) % Players.size()) > 0) {
            int NumContestFailedOfNextPlayer = NumContestFailed.get((PlayerNumber + 1) % Players.size());
            NumContestFailed.set((PlayerNumber + 1) % Players.size(), NumContestFailedOfNextPlayer - 1);
            PlayerNumber = (PlayerNumber + 1) % Players.size();
        }
        PlayerNumber = (PlayerNumber + 1) % Players.size();
        // Notify the front-end or other players that the turn has ended and it's the next player's turn
        CurrentPlayer = Players.get(PlayerNumber);
        System.out.println("It's now player " + PlayerNumber + "'s turn.");
        System.out.println("It's now player " + getCurrentPlayer().getId() + "'s turn.");
        this.endTurn = false;

    }


    @Override
    public void dealContest(boolean ContestSucceed) {
        if (ContestSucceed){
            this.CurrentPlayer.BeContested();
            NumContestFailed.set((PlayerNumber), NumContestFailed.get((PlayerNumber)));
        }
        this.CurrentPlayer.NotContested();
        System.out.println("Player " + this.CurrentPlayer.getId() + " contest result: " + (ContestSucceed ? "Valid" : "Invalid"));
    }
}






