package com.example.scrabble.use_case;

import com.example.scrabble.use_case.remove_letter.RemoveLetterInteractor;
import com.example.scrabble.use_case.remove_letter.RemoveLetterInputData;
import com.example.scrabble.use_case.remove_letter.RemoveLetterOutputData;
import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveLetterInteractorTest {

    @Mock
    private GameDataAccess gameDao;

    @Mock
    private Game game;

    @Mock
    private Play play;

    @Mock
    private Player player;

    @Mock
    private Board board;

    @Mock
    private Tile tile;

    @Mock
    private Move move;

    @InjectMocks
    private RemoveLetterInteractor removeLetterInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Letter testLetter = new Letter('A', 0);

        // Mock game behavior
        when(gameDao.get(anyInt())).thenReturn(game);
        when(game.getCurrentPlay()).thenReturn(play);
        when(game.getBoard()).thenReturn(board);
        when(play.getPlayer()).thenReturn(player);

        // Mock board behavior
        when(board.getCell(anyInt(), anyInt())).thenReturn(tile);

        // Mock tile behavior
        when(tile.getLetter()).thenReturn(testLetter);

        // Mock play behavior
        List<Move> moves = new ArrayList<>();
        moves.add(move);
        when(play.getMoves()).thenReturn(moves);

        // Mock move behavior
        when(move.getLetter()).thenReturn(testLetter);
        when(move.getX()).thenReturn(0);
        when(move.getY()).thenReturn(0);
    }

    @Test
    void testExecuteValidMove() {
        RemoveLetterInputData inputData = new RemoveLetterInputData(1, 0, 0);

        RemoveLetterOutputData outputData = removeLetterInteractor.execute(inputData);

        assertNotNull(outputData);
        assertTrue(outputData.isRemoveSuccessful());
        verify(play, times(1)).removeMove(0, 0);
        Letter verifyLetter = game.getBoard().getCell(0, 0).getLetter();
        verify(player, times(1)).addLetter(verifyLetter);
        verify(tile, times(1)).removeLetter();
        verify(gameDao, times(1)).update(game);
    }

    @Test
    void testExecuteInvalidMove() {
        // Mock invalid move
        when(move.getX()).thenReturn(1);  // Change to a different coordinate

        RemoveLetterInputData inputData = new RemoveLetterInputData(1, 0, 0);

        assertThrows(InvalidPlayException.class, () -> removeLetterInteractor.execute(inputData));
        verify(play, never()).removeMove(anyInt(), anyInt());
        verify(player, never()).addLetter(new Letter(anyChar(),anyInt()));
        verify(tile, never()).removeLetter();
        verify(gameDao, never()).update(game);
    }
}
