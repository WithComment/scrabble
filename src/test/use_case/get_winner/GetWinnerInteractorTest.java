package test.use_case.get_winner;

import entity.LeaderboardEntry;
import entity.Player;
import use_case.get_leaderboard.GetLeaderboardInputData;
import use_case.get_winner.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class GetWinnerInteractorTest implements GetWinnerOutputBoundary {

    private GetWinnerInputData inputData;
    private GetWinnerInteractor interactor;
    private ArrayList<LeaderboardEntry> winners;

    public void prepareView(GetWinnerOutputData outputData) {
        winners = outputData.getWinner();
    }

    @BeforeEach
    public void setUp() {
        interactor = new GetWinnerInteractor(this);
    }

    @Test
    public void singleWinnerIdentifiedCorrectly() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(1));
        players.get(0).addScore(20);
        players.add(new Player(2));
        players.get(1).addScore(15);
        inputData = new GetWinnerInputData(players);

        interactor.execute(inputData);

        List<LeaderboardEntry> expectedWinner = new ArrayList<>();
        expectedWinner.add(new LeaderboardEntry(players.get(0), 20));
        assertEquals(expectedWinner, winners);
    }

    @Test
    public void multipleWinnersIdentifiedInCaseOfTie() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(1));
        players.get(0).addScore(20);
        players.add(new Player(2));
        players.get(1).addScore(25);
        inputData = new GetWinnerInputData(players);

        interactor.execute(inputData);

        List<LeaderboardEntry> expectedWinners = new ArrayList<>();
        expectedWinners.add(new LeaderboardEntry(players.get(0), 20));
        expectedWinners.add(new LeaderboardEntry(players.get(1), 20));
        assertEquals(expectedWinners, winners);
    }

    @Test
    public void noWinnersIdentifiedWhenNoPlayersPresent() {
        ArrayList<Player> players = new ArrayList<>();
        inputData = new GetWinnerInputData(players);

        interactor.execute(inputData);

        List<LeaderboardEntry> expectedWinners = new ArrayList<>();
        assertEquals(expectedWinners, winners);
    }
}