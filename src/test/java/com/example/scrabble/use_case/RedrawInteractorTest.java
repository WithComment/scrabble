package com.example.scrabble.use_case;

import com.example.scrabble.use_case.redraw_letters.RedrawInteractor;
import com.example.scrabble.use_case.redraw_letters.RedrawInputData;
import com.example.scrabble.use_case.redraw_letters.RedrawOutputData;
import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RedrawInteractorTest {

    private GameDataAccess gameDao;
    private RedrawInteractor redrawInteractor;
    private Game mockGame;
    private Player mockPlayer;
    private LetterBag mockLetterBag;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        gameDao = mock(GameDataAccess.class);
        redrawInteractor = new RedrawInteractor(gameDao);
        mockGame = mock(Game.class);
        mockPlayer = mock(Player.class);
        mockLetterBag = mock(LetterBag.class);

        // Set up behavior of mocks
        when(mockGame.getCurrentPlayer()).thenReturn(mockPlayer);
        when(mockGame.getLetterBag()).thenReturn(mockLetterBag);
        when(gameDao.get(anyInt())).thenReturn(mockGame);
    }

    @Test
    void testRedrawSuccessful() {
        // Given: The player's inventory and the letter bag
        List<Letter> playerInventory = Arrays.asList(new Letter('A', 1), new Letter('B', 3));
        List<Letter> newLetters = Arrays.asList(new Letter('X', 8), new Letter('Y', 4));

        when(mockPlayer.getInventory()).thenReturn(playerInventory);
        when(mockLetterBag.getLength()).thenReturn(7);
        when(mockLetterBag.drawLetters(2)).thenReturn(newLetters);

        // When: Redraw is executed
        RedrawInputData inputData = new RedrawInputData(1, Arrays.asList('A', 'B'));
        RedrawOutputData outputData = redrawInteractor.execute(inputData);

        // Then: Verify the interactions and results
        verify(mockPlayer).removeLetter(any(ArrayList.class));
        verify(mockPlayer).addLetter(newLetters);
        verify(mockLetterBag).addLetters(anyList());
        assertEquals(newLetters, outputData.getNewLetters());
        assertEquals(true, outputData.isSuccessful());
    }

    @Test
    void testRedrawUnsuccessful() {
        // Given: Letter bag has less than 7 letters
        when(mockLetterBag.getLength()).thenReturn(5);

        // When: Redraw is executed
        RedrawInputData inputData = new RedrawInputData(1, Arrays.asList('A', 'B'));
        RedrawOutputData outputData = redrawInteractor.execute(inputData);

        // Then: Verify the redraw was unsuccessful
        assertEquals(false, outputData.isSuccessful());
        assertEquals(0, outputData.getNewLetters().size());
    }
}

