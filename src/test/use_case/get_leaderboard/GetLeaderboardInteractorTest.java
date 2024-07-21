package test.use_case.get_leaderboard;

import entity.LeaderboardEntry;
import entity.Player;
import use_case.get_leaderboard.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

public class GetLeaderboardInteractorTest implements GetLeaderboardOutputBoundary{

    private GetLeaderboardInputData inputData;
    private GetLeaderboardInteractor interactor;
    private ArrayList<LeaderboardEntry> leaderboard;

    public void prepareView(GetLeaderboardOutputData outputData) {
        leaderboard = outputData.getLeaderboard();
    }

    @BeforeEach
    public void setUp() {
        interactor = new GetLeaderboardInteractor(this);
    }

    @Test
    public void leaderboardWithMultiplePlayers_SortedDescendingByScore() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(1));
        players.get(0).addScore(10);
        players.add(new Player(2));
        players.get(1).addScore(15);
        players.add(new Player(3));
        players.get(2).addScore(15);

        inputData = new GetLeaderboardInputData(players);

        interactor.execute(inputData);

        List<LeaderboardEntry> expectedLeaderboard = new ArrayList<>();
        expectedLeaderboard.add(new LeaderboardEntry(players.get(1), 15));
        expectedLeaderboard.add(new LeaderboardEntry(players.get(0), 10));
        expectedLeaderboard.add(new LeaderboardEntry(players.get(2), 5));

        Assert.assertEquals(expectedLeaderboard, leaderboard);
    }

    @Test
    public void leaderboardWithSinglePlayer_ShouldReturnSingleEntry() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(1));
        players.get(0).addScore(10);

        inputData = new GetLeaderboardInputData(players);

        interactor.execute(inputData);

        List<LeaderboardEntry> expectedLeaderboard = new ArrayList<>();
        expectedLeaderboard.add(new LeaderboardEntry(players.get(0), 10));

        Assert.assertEquals(expectedLeaderboard, leaderboard);
    }

    @Test
    public void emptyLeaderboard_ShouldReturnEmptyList() {
        ArrayList<Player> players = new ArrayList<>();

        inputData = new GetLeaderboardInputData(players);

        interactor.execute(inputData);

        List<LeaderboardEntry> expectedLeaderboard = new ArrayList<>();

        Assert.assertEquals(expectedLeaderboard, leaderboard);
    }

}