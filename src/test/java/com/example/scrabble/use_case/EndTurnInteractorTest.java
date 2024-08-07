package com.example.scrabble.use_case;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.LetterBag;
import com.example.scrabble.entity.Move;
import com.example.scrabble.entity.Play;
import com.example.scrabble.entity.Player;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import com.example.scrabble.use_case.end_turn.EndTurnInputData;
import com.example.scrabble.use_case.end_turn.EndTurnOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class EndTurnInteractorTest {

    @Mock
    private GameDataAccess gameDataAccess;

    @Mock
    private Game game;

    @Mock
    private Board board;

    @Mock
    private Player player;

    @Mock
    private Move move;

    @Mock
    private Play play;

    @InjectMocks
    private EndTurnInteractor endTurnInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking game behavior
        when(game.getBoard()).thenReturn(board);
        when(game.getPlayers()).thenReturn(Arrays.asList(player));
        when(game.getId()).thenReturn(1);
        when(move.getX()).thenReturn(0);
        when(move.getY()).thenReturn(0);

        // Mocking gameDataAccess behavior
        when(gameDataAccess.get(anyInt())).thenReturn(game);
    }

    @Test
    void testExecute() {
        List<Move> moves = new LinkedList<>() {{
            add(move);
        }};
        EndTurnInputData inputData = new EndTurnInputData(1);

        when(game.isEndTurn()).thenReturn(true);
        when(game.getCurrentPlayer()).thenReturn(new Player(0));
        when(player.getInventory()).thenReturn(new ArrayList<>());
        when(game.getLetterBag()).thenReturn(new LetterBag());
        when(game.getCurrentPlay()).thenReturn(play);
        when(play.getMoves()).thenReturn(moves);

        EndTurnOutputData outputData = endTurnInteractor.execute(inputData);

        assertNotNull(outputData);
        verify(gameDataAccess, times(1)).update(game);
        verify(board, times(1)).confirm(0, 0);
        verify(game, times(1)).endTurn();
        verify(game, times(1)).startTurn();
    }
}

