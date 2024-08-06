package com.example.scrabble.use_case;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.use_case.redraw_letters.RedrawInteractor;
import com.example.scrabble.use_case.redraw_letters.RedrawInputData;
import com.example.scrabble.use_case.redraw_letters.RedrawOutputData;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedrawInteractorTest {

    @Mock
    private GameDataAccess gameDao;

    @Mock
    private Game game;

    @Mock
    private Player player;

    @Mock
    private LetterBag letterBag;

    @InjectMocks
    private RedrawInteractor redrawInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_DrawSuccessful() {
        // Arrange
        List<Letter> inventory = Arrays.asList(new Letter('A', 1), new Letter('B', 3));
        List<Letter> lettersToRedraw = Collections.singletonList(new Letter('A', 1));
        List<Letter> newLetters = Collections.singletonList(new Letter('C', 3));

        when(gameDao.get(1)).thenReturn(game);
        when(game.getCurrentPlayer()).thenReturn(player);
        when(player.getInventory()).thenReturn(inventory);
        when(game.getLetterBag()).thenReturn(letterBag);
        when(letterBag.getLength()).thenReturn(10);
        when(letterBag.drawLetters(1)).thenReturn(newLetters);

        RedrawInputData inputData = new RedrawInputData(1, Arrays.asList("A"));

        // Act
        RedrawOutputData outputData = redrawInteractor.execute(inputData);

        // Assert
        assertTrue(outputData.isSuccessful());
        assertEquals(newLetters, outputData.getNewLetters());

        // Verify that letters were added back to the letter bag
        verify(letterBag).addLetters(lettersToRedraw);

        // Verify that the player's inventory was updated
        ArgumentCaptor<List<Letter>> captor = ArgumentCaptor.forClass(List.class);
        verify(player).removeLetter(captor.capture());
        assertEquals(lettersToRedraw, captor.getValue());

        verify(player).addLetter(newLetters);

        // Verify that the game was updated
        verify(gameDao).update(game);
    }

    @Test
    void testExecute_DrawUnsuccessful() {
        // Arrange
        List<Letter> inventory = Arrays.asList(new Letter('A', 1), new Letter('B', 3));

        when(gameDao.get(1)).thenReturn(game);
        when(game.getCurrentPlayer()).thenReturn(player);
        when(player.getInventory()).thenReturn(inventory);
        when(game.getLetterBag()).thenReturn(letterBag);
        when(letterBag.getLength()).thenReturn(3);

        RedrawInputData inputData = new RedrawInputData(1, Arrays.asList("A"));

        // Act
        RedrawOutputData outputData = redrawInteractor.execute(inputData);

        // Assert
        assertFalse(outputData.isSuccessful());
        assertTrue(outputData.getNewLetters().isEmpty());

        // Verify that letters were not redrawn
        verify(letterBag, never()).drawLetters(anyInt());

        // Verify that the game was updated
        verify(gameDao).update(game);
    }
}


