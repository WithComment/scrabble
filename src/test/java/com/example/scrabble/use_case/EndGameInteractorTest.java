package com.example.scrabble.use_case;

import com.example.scrabble.use_case.end_game.EndGameInteractor;
import com.example.scrabble.use_case.end_game.EndGameInputData;
import com.example.scrabble.use_case.end_game.EndGameOutputData;
import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndGameInteractorTest {

    @Mock
    private GameDataAccess gameDao;

    @Mock
    private Game game;

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    @Mock
    private Letter letter;

    @InjectMocks
    private EndGameInteractor endGameInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteWithValidEndGame() throws InvalidPlayException {
        when(gameDao.get(anyInt())).thenReturn(game);

        // Mock players
        when(game.getPlayers()).thenReturn(Arrays.asList(player1, player2));

        // Player 1 has no letters left, Player 2 has 1 letter worth 5 points
        when(player1.getInventory()).thenReturn(Collections.emptyList());
        when(player1.getScore()).thenReturn(50);
        when(player2.getInventory()).thenReturn(Collections.singletonList(letter));
        when(player2.getScore()).thenReturn(45);
        when(letter.getPoints()).thenReturn(5);

        EndGameInputData inputData = new EndGameInputData(1);

        // Execute the end game interactor
        EndGameOutputData outputData = endGameInteractor.execute(inputData);

        // Verify that Player 1 wins
        assertEquals(1, outputData.getWinners().size());
        assertTrue(outputData.getWinners().contains(player1.getId()));
    }

    @Test
    void testExecuteWithInvalidEndGame() {
        letter = new Letter('A', 2);
        List<Letter> letters = Arrays.asList(letter);

        when(gameDao.get(anyInt())).thenReturn(game);

        // Mock players
        when(game.getPlayers()).thenReturn(Arrays.asList(player1, player2));

        // Neither player has emptied their inventory
        when(player1.getInventory()).thenReturn(letters);
        when(player2.getInventory()).thenReturn(letters);

        EndGameInputData inputData = new EndGameInputData(1);

        // Verify that InvalidPlayException is thrown
        assertThrows(InvalidPlayException.class, () -> endGameInteractor.execute(inputData));
    }
}
