package test.use_case.end_turn;

import static org.junit.jupiter.api.Assertions.*;

import entity.Player;
import entity.TurnManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.EndTurn.TurnManagerInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the TurnManager class.
 */
public class TurnManagerTest {

    private TurnManager turnManager;
    private List<Player> players;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void setUp() {
        player1 = new Player(0);
        player2 = new Player(1);
        player3 = new Player(2);
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        turnManager = new TurnManager(players);
    }

    @Test
    public void testInitialCurrentPlayer() {
        assertNull(turnManager.GetCurrentPlayer(), "Initial current player should be null.");
    }

    @Test
    public void testEndTurn() {
        turnManager.endTurn();
        assertTrue(turnManager.isEndTurn(), "End turn flag should be true after calling endTurn.");
    }

    @Test
    public void testStartTurn() {
        turnManager.startTurn();
        assertFalse(turnManager.isEndTurn(), "End turn flag should be false after calling startTurn.");
        assertNotNull(turnManager.GetCurrentPlayer(), "Current player should not be null after starting the turn.");
    }

    @Test
    public void testDealContestSuccess() {
        turnManager.startTurn();
        turnManager.dealContest(true);
        assertEquals(1, turnManager.getPlayersNumContestFailed(turnManager.getCurrentPlayerNum()), "Contest failure count should be incremented.");
        assertEquals(player1.getScore(), player1.getScore() - player1.unstableScore, "Player score should be updated correctly after contest success.");
    }

    @Test
    public void testDealContestFailure() {
        turnManager.startTurn();
        turnManager.dealContest(false);
        assertEquals(0, turnManager.getPlayersNumContestFailed(turnManager.getCurrentPlayerNum()), "Contest failure count should not be incremented.");
    }

    @Test
    public void testPlayerRotationOnStartTurn() {
        turnManager.startTurn();
        assertEquals(player1, turnManager.GetCurrentPlayer(), "Player 1 should be the current player at the start.");

        turnManager.endTurn();
        turnManager.startTurn();
        assertEquals(player2, turnManager.GetCurrentPlayer(), "Player 2 should be the current player after ending Player 1's turn.");

        turnManager.endTurn();
        turnManager.startTurn();
        assertEquals(player3, turnManager.GetCurrentPlayer(), "Player 3 should be the current player after ending Player 2's turn.");

        turnManager.endTurn();
        turnManager.startTurn();
        assertEquals(player1, turnManager.GetCurrentPlayer(), "Player 1 should be the current player again after ending Player 3's turn.");
    }

    @Test
    public void testSkipContestedPlayers() {
        turnManager.startTurn();
        turnManager.dealContest(true);
        turnManager.endTurn();
        turnManager.startTurn();

        assertEquals(player2, turnManager.GetCurrentPlayer(), "Player 2 should be the current player after Player 1's turn.");

        turnManager.dealContest(true);
        turnManager.endTurn();
        turnManager.startTurn();

        assertEquals(player3, turnManager.GetCurrentPlayer(), "Player 3 should be the current player after Player 2's turn.");

        turnManager.dealContest(true);
        turnManager.endTurn();
        turnManager.startTurn();

        assertEquals(player1, turnManager.GetCurrentPlayer(), "Player 1 should be the current player again after all players contested.");
    }
}
