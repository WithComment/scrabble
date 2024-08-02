package com.example.scrabble.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    private List<Player> players;
    private TurnManager turnManager;

    @BeforeEach
    void setUp() {
        // Initialize test data
        players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));

        turnManager = new TurnManager(players);
    }

    @Test
    void testEndTurn() {
        turnManager.endTurn();
        assertEquals(players.get(1), turnManager.getCurrentPlayer());
        assertTrue(turnManager.isEndTurn());
    }

    @Test
    void testStartTurn() {
        turnManager.startTurn();
        assertFalse(turnManager.isEndTurn());
        assertNotNull(turnManager.getCurrentPlay());
    }

    @Test
    void testContestFailureUpdate() {
        turnManager.ContestFailureUpdate(0);
        assertEquals(1, turnManager.getPlayersNumContestFailed(0));
    }

    @Test
    void testDealContest() {
        turnManager.dealContest(false);
        assertEquals(0, turnManager.getPlayersNumContestFailed(turnManager.getCurrentPlayerNum()));
    }

    @Test
    void testGetCurrentPlayer() {
        assertEquals(players.get(0), turnManager.getCurrentPlayer());
    }

    @Test
    void testUpdatePlayer() {
        Player newPlayer = new Player("Player4");
        turnManager.updatePlayer(newPlayer);
        assertTrue(turnManager.GetPlayers().contains(newPlayer));
    }
}

