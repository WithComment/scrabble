package use_case.EndTurn;

import entity.Player;
import entity.TurnManager;

/**
 * Implements the use case interactor for retrieving and presenting the game leaderboard.
 * This class is responsible for fetching the leaderboard data, sorting it, and then
 * passing it to the presenter for display.
 */
public class EndTurnInteractor implements GetEndTurnInputBoundary {
    private final GetEndTurnOutputBoundary presenter;

    /**
     * Constructs a GetLeaderboardInteractor with a specified presenter.
     * @param presenter The presenter to which the sorted leaderboard will be passed.
     */
    public EndTurnInteractor(GetEndTurnOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(GetEndTurnOutPutData getEndTurnInputData) {
        TurnManagerInteractor turnManagerInteractor = new TurnManagerInteractor(new TurnManager(getEndTurnInputData.getPlayers()));
        for(Player player : getEndTurnInputData.players){
            turnManagerInteractor.getTurnManager().updatePlayer(player);
        }

        if (turnManagerInteractor.isEndTurn()) {
            Player CurrentPlayer = turnManagerInteractor.getTurnManager().getCurrentPlayer();
            if(getEndTurnInputData.isContestSucceed){
                CurrentPlayer.eraseTempScore();
            }else {
                CurrentPlayer.confirmTempScore();
            }
            int ToDraw = 7 - CurrentPlayer.getInventory().size();
            CurrentPlayer.addLetter(getEndTurnInputData.getLetterBag().drawLetters(ToDraw));
            turnManagerInteractor.endTurn();
            turnManagerInteractor.startTurn();
        }

        if(getEndTurnInputData.isContest){
            turnManagerInteractor.dealContest(getEndTurnInputData.isContestSucceed);
        }
    }

    /**
     * Executes the use case of getting the leaderboard. It retrieves player data,
     * creates leaderboard entries for each player, sorts these entries in descending
     * order of scores, and then passes the sorted list to the presenter.
     * @param input Contains the list of players from which to generate the leaderboard.
     */

}