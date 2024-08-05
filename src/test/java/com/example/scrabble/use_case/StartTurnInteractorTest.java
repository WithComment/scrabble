package com.example.scrabble.use_case;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.use_case.start_turn.StartTurnInputData;
import com.example.scrabble.use_case.start_turn.StartTurnInteractor;

import com.example.scrabble.use_case.start_turn.StartTurnOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StartTurnInteractorTest {

    private StartTurnInteractor startTurnInteractor;
    private GameDataAccess gameDataAccess;
    private StartTurnInputData startTurnInputData;
    private Game mockGame;

    @BeforeEach
    public void setUp() {
        gameDataAccess = mock(GameDataAccess.class);
        startTurnInteractor = new StartTurnInteractor(gameDataAccess);

        // Create a mock game and input data
        mockGame = mock(Game.class);
        startTurnInputData = mock(StartTurnInputData.class);

        // Mock the input data to return a specific game ID
        when(startTurnInputData.getGameId()).thenReturn(0);
        // Mock the game data access to return the mock game when fetching by ID
        when(gameDataAccess.get(0)).thenReturn(mockGame);
    }

    @Test
    public void testExecute() {
        // Execute the interactor
        StartTurnOutputData outputData = startTurnInteractor.execute(startTurnInputData);

        // Verify that the correct methods were called on the game object
        verify(mockGame).endTurn();
        verify(mockGame).startTurn();

        // Verify that the game was updated in the data access object
        verify(gameDataAccess).update(mockGame);

        // Verify the output data contains the correct game ID
        assertEquals(0, outputData.getGameId());
    }
}

