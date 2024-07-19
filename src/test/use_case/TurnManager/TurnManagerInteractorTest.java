package test.use_case.TurnManager;

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
public class TurnManagerInteractorTest {

    private TurnManagerInteractor turnManagerInteractor;
    private List<Player> players;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void setUp() {
        player1 = new Player(1);
        player2 = new Player(2);
        player3 = new Player(3);
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        turnManagerInteractor = new TurnManagerInteractor(new TurnManager(players));
    }

    @Test
    public void testInitialCurrentPlayer() {
        assertNull(turnManagerInteractor.getCurrentPlayer(), "Initial current player should be null.");
    }

    @Test
    public void testEndTurn() {
        turnManagerInteractor.endTurn();
        assertTrue(turnManagerInteractor.isEndTurn());
    }

    @Test
    public void testStartTurn() {
        turnManagerInteractor.startTurn();
        assertFalse(turnManagerInteractor.isEndTurn(), "End turn flag should be false after calling startTurn.");
        assertNotNull(turnManagerInteractor.getCurrentPlayer(), "Current player should not be null after starting the turn.");
    }

    @Test
    public void testDealContestSuccess() {
        turnManagerInteractor.startTurn();
        int NumContestFailed = turnManagerInteractor.getPlayersNumContestFailed(turnManagerInteractor.getCurrentPlayerNum());
        turnManagerInteractor.dealContest(true);
        assertEquals(player1.getScore(), player1.getScore() - player1.unstableScore, "Player score should be updated correctly after contest success.");
    }

    @Test
    public void testDealContestFailure() {
        turnManagerInteractor.startTurn();
        int NumContestFailed = turnManagerInteractor.getPlayersNumContestFailed(turnManagerInteractor.getCurrentPlayerNum());
        turnManagerInteractor.dealContest(false);
        assertEquals(NumContestFailed + 1, turnManagerInteractor.getPlayersNumContestFailed(turnManagerInteractor.getCurrentPlayerNum()), "Contest failure count should not be incremented.");
    }

    @Test
    public void testPlayerRotationOnStartTurn() {
        turnManagerInteractor.startTurn();
        assertEquals(player1, turnManagerInteractor.getCurrentPlayer(), "Player 1 should be the current player at the start.");

        turnManagerInteractor.endTurn();
        turnManagerInteractor.startTurn();
        assertEquals(player2, turnManagerInteractor.getCurrentPlayer(), "Player 2 should be the current player after ending Player 1's turn.");

        turnManagerInteractor.endTurn();
        turnManagerInteractor.startTurn();
        assertEquals(player3, turnManagerInteractor.getCurrentPlayer(), "Player 3 should be the current player after ending Player 2's turn.");

        turnManagerInteractor.endTurn();
        turnManagerInteractor.startTurn();
        assertEquals(player1, turnManagerInteractor.getCurrentPlayer(), "Player 1 should be the current player again after ending Player 3's turn.");
    }

    @Test
    public void testSkipContestedPlayers() {
        turnManagerInteractor.startTurn();
        turnManagerInteractor.dealContest(true);
        turnManagerInteractor.endTurn();
        turnManagerInteractor.startTurn();

        assertEquals(player2, turnManagerInteractor.getCurrentPlayer(), "Player 2 should be the current player after Player 1's turn.");

        turnManagerInteractor.dealContest(true);
        turnManagerInteractor.endTurn();
        turnManagerInteractor.startTurn();

        assertEquals(player3, turnManagerInteractor.getCurrentPlayer(), "Player 3 should be the current player after Player 2's turn.");

        turnManagerInteractor.dealContest(true);
        turnManagerInteractor.endTurn();
        turnManagerInteractor.startTurn();

        assertEquals(player1, turnManagerInteractor.getCurrentPlayer(), "Player 1 should be the current player again after all players contested.");
    }
}
