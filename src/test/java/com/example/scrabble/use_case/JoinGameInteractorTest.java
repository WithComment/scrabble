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
        when(game.getLetterBag()).thenReturn(new LetterBag());
    }

    @Test
    void testExecute() {
        String playerName = "NewPlayer";
        JoinGameInputData inputData = new JoinGameInputData(playerName,1);

        JoinGameOutputData outputData = joinGameInteractor.execute(inputData);

        // Verify that a new player was added to the game
        verify(game, times(1)).addPlayer(any(Player.class));

        // Verify that the game state was updated
        verify(gameDataAccess, times(1)).update(game);

        // Check the output data
        assertEquals(playerName, outputData.getPlayerName());
    }
}
