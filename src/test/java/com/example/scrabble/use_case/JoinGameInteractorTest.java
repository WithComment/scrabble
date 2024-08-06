package com.example.scrabble.use_case;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.join_game.JoinGameInteractor;
import com.example.scrabble.use_case.join_game.JoinGameInputData;
import com.example.scrabble.use_case.join_game.JoinGameOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class JoinGameInteractorTest {

    @Mock
    private GameDataAccess gameDataAccess;

    @Mock
    private Game game;

    @InjectMocks
    private JoinGameInteractor joinGameInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking game behavior
        when(gameDataAccess.get(anyInt())).thenReturn(game);
        when(gameDataAccess.get(-1)).thenThrow(new IllegalArgumentException("Game with the specified ID does not exist."));
        when(game.getLetterBag()).thenReturn(new LetterBag());
    }

    @Test
    void testExecute() {
        String playerName = "NewPlayer";
        JoinGameInputData inputData = new JoinGameInputData(playerName,1);

        JoinGameOutputData outputData = joinGameInteractor.execute(inputData);

        // Verify that a new player was added to the game
        verify(game, times(1)).addPlayer(any(String.class));

        // Verify that the game state was updated
        verify(gameDataAccess, times(1)).update(game);

        // Check the output data
        assertEquals(playerName, outputData.getPlayerName());
    }

    @Test
    void testExecute_gameNotFound() {
        String playerName = "NewPlayer";
        JoinGameInputData inputData = new JoinGameInputData(playerName, -1);

        // Act & Assert: Check that an IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            joinGameInteractor.execute(inputData);
        });

        // Verify that gameDAO.get(-1) was called
        verify(gameDataAccess, times(1)).get(-1);

        // Verify that no player was added to the game, as the game doesn't exist
        verify(game, never()).addPlayer(anyString());

        // Verify that the game state was not updated
        verify(gameDataAccess, never()).update(game);
    }
}
