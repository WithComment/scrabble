package com.example.scrabble.use_case;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.use_case.contest.ContestInputData;
import com.example.scrabble.use_case.contest.ContestInteractor;
import com.example.scrabble.use_case.contest.ContestOutputData;
import com.example.scrabble.use_case.contest.ContestException;
import com.example.scrabble.use_case.contest.WordValidationException;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.Play;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ContestInteractorTest {

    private ContestInteractor contestInteractor;
    private GameDataAccess gameDao;
    private Game mockGame;
    private Player mockPlayer;
    private Play mockPlay;

    @BeforeEach
    public void setUp() {
        gameDao = mock(GameDataAccess.class);
        contestInteractor = Mockito.spy(new ContestInteractor(gameDao));
        mockGame = mock(Game.class);
        mockPlayer = mock(Player.class);
        mockPlay = mock(Play.class);

        when(gameDao.get(anyInt())).thenReturn(mockGame);
        when(mockGame.getPlayer(anyInt())).thenReturn(mockPlayer);
        when(mockGame.getLastPlay()).thenReturn(new Play(mockPlayer));
    }

    @Test
    public void testExecute_AllWordsValid_ThrowsContestException() throws Exception {
        // Setup the input data
        ContestInputData inputData = new ContestInputData(1, 1, true);
        List<String> validWords = Arrays.asList("VALID", "WORDS");

        when(mockPlay.getWords()).thenReturn(validWords);
        when(mockGame.getLastPlay()).thenReturn(mockPlay);
        doReturn(true).when(contestInteractor).wordIsValid(anyString());

        // Execute the method
        ContestOutputData result = contestInteractor.execute(inputData);

        // Verify that fail() was called
        verify(contestInteractor).fail();

        // Verify that gameDao.update(game) was called
        verify(gameDao).update(mockGame);

        // Verify the result
        assertTrue(result.getInvalidWords().isEmpty());
        assertEquals(0, result.getInvalidWords().size());
    }

    @Test
    public void testExecute_InvalidWords_ReturnsInvalidWords() throws Exception {
        // Setup the input data
        ContestInputData inputData = new ContestInputData(1, 1, true);
        List<String> words = Arrays.asList("VALID", "INVALID");
        when(mockGame.removeLastPlay()).thenReturn(mockPlay);
        when(mockGame.getLastPlay()).thenReturn(mockPlay);
        when(mockPlay.getWords()).thenReturn(words);
        when(mockPlay.getPlayer()).thenReturn(mockPlayer);
        when(mockGame.getPlayer(anyInt())).thenReturn(mockPlayer);
        doReturn(true).when(contestInteractor).wordIsValid("VALID");
        doReturn(false).when(contestInteractor).wordIsValid("INVALID");

        // Execute the method
        ContestOutputData result = contestInteractor.execute(inputData);

        // Verify that the fail() method was NOT called
        verify(contestInteractor, never()).fail();

        // Verify that gameDao.update(game) was called
        verify(gameDao).update(mockGame);

        // Verify the result
        assertFalse(result.getInvalidWords().isEmpty());
        assertEquals(1, result.getInvalidWords().size());
        assertEquals("INVALID", result.getInvalidWords().get(0));
    }

    @Test
    public void testExecute_NoContestIncreasesNumContests() {
        // Setup the input data
        ContestInputData inputData = new ContestInputData(1, 1, false);

        when(mockGame.getNumContests()).thenReturn(0);
        when(mockGame.getNumPlayers()).thenReturn(3);

        // Execute the method
        ContestOutputData result = contestInteractor.execute(inputData);

        // Verify that the game's number of contests is increased
        verify(mockGame).increaseNumContests();

        // Verify that gameDao.update(game) was called
        verify(gameDao).update(mockGame);

        // Verify the result
        assertTrue(result.getInvalidWords().isEmpty());
    }

    @Test
    public void testExecute_AllPlayersHaveContested_ReturnsContestSuccessful() {
        // Setup the input data
        ContestInputData inputData = new ContestInputData(1, 1, false);

        when(mockGame.getNumContests()).thenReturn(2);
        when(mockGame.getNumPlayers()).thenReturn(3);

        // Execute the method
        ContestOutputData result = contestInteractor.execute(inputData);

        // Verify that the game's number of contests is increased
        verify(mockGame).increaseNumContests();

        // Verify that gameDao.update(game) was called
        verify(gameDao).update(mockGame);

        // Verify the result
        assertTrue(result.getInvalidWords().isEmpty());
        assertTrue(result.getInvalidWords().isEmpty());
    }
}



