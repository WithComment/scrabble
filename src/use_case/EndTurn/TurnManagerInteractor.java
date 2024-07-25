package use_case.EndTurn;

import entity.LeaderboardEntry;
import entity.Player;
import entity.TurnManager;
import use_case.get_leaderboard.GetLeaderboardInputData;
import use_case.get_leaderboard.GetLeaderboardOutputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Interactor for managing the turns of players in the game.
 * Handles the logic for ending turns, starting new turns, and dealing with contests.
 */
public class TurnManagerInteractor implements EndTurn, DealingContest, StartTurn {

    private final TurnManager turnManager;

    /**
     * Constructs a TurnManagerInteractor with the given TurnManager.
     *
     * @param turnManager The TurnManager entity handling turn details
     */
    public TurnManagerInteractor(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    /**
     * Ends the current turn.
     */
    @Override
    public void endTurn() {
        turnManager.endTurn();
        // Additional logic related to ending the turn can be added here
    }

    /**
     * Starts a new turn by updating the current player and resetting necessary flags.
     */
    @Override
    public void startTurn() {
        turnManager.startTurn();
        // Additional logic related to starting the turn can be added here
    }

    /**
     * Deals with the result of a contest.
     *
     * @param contestSucceed A boolean indicating whether the contest succeeded
     */
    @Override
    public void dealContest(boolean contestSucceed) {
        turnManager.dealContest(contestSucceed);
        // Additional logic related to dealing with contests can be added here
    }

    /**
     * Adds a player to the game.
     *
     * @param player The player to be added
     */
    public void addPlayer(Player player) {
        turnManager.updatePlayer(player);
    }

    /**
     * Returns the current player.
     *
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    /**
     * Updates the contest failure count for a player.
     *
     * @param playerNumber The number of the player whose contest failure count is being updated
     */
    public void contestFailureUpdate(int playerNumber) {
        turnManager.ContestFailureUpdate(playerNumber);
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public boolean isEndTurn(){
        return turnManager.isEndTurn();
    }

    public int getPlayersNumContestFailed(int playerNumber){
        return turnManager.getPlayersNumContestFailed(playerNumber);
    }

    public int getCurrentPlayerNum(){
        return turnManager.getCurrentPlayerNum();
    }

    public List<Player> getPlayers(){
        return turnManager.GetPlayers();
    }

    public void execute(TurnManagerInputData turnManagerInputData){
        for(Player player : turnManagerInputData.players){
            this.turnManager.updatePlayer(player);
        }

        if (isEndTurn()) {
            Player CurrentPlayer = turnManager.getCurrentPlayer();
            if(turnManagerInputData.isContestSucceed){
                CurrentPlayer.eraseTempScore();
            }else {
                CurrentPlayer.confirmTempScore();
            }
            int ToDraw = 7 - CurrentPlayer.getInventory().size();
            CurrentPlayer.addLetter(turnManagerInputData.getLetterBag().drawLetters(ToDraw));
            endTurn();
            startTurn();
        }

        if(turnManagerInputData.isContest){
            dealContest(turnManagerInputData.isContestSucceed);
        }
    }
}

