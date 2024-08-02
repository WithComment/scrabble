package com.example.scrabble.use_case;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Board;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.TurnManager;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import com.example.scrabble.use_case.end_turn.GetEndTurnInputData;
import com.example.scrabble.use_case.end_turn.GetEndTurnOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
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
    private TurnManager turnManager;

    @Mock
    private Board board;

    @Mock
    private Player player;

    @InjectMocks
    private EndTurnInteractor endTurnInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking game behavior
        when(game.getTurnManager()).thenReturn(turnManager);
        when(game.getBoard()).thenReturn(board);
        when(game.getPlayers()).thenReturn(Arrays.asList(player));
        when(game.getId()).thenReturn(1);

        // Mocking gameDataAccess behavior
        when(gameDataAccess.get(anyInt())).thenReturn(game);
    }

    @Test
    void testExecute() {
        List<List<Integer>> wordsToBeConfirmed = new ArrayList<>();
        wordsToBeConfirmed.add(Arrays.asList(0, 0));
        GetEndTurnInputData inputData = new GetEndTurnInputData(1, wordsToBeConfirmed);

        when(turnManager.isEndTurn()).thenReturn(true);
        when(player.getInventory()).thenReturn(new ArrayList<>());

        GetEndTurnOutputData outputData = endTurnInteractor.execute(inputData);

        assertNotNull(outputData);
        verify(gameDataAccess, times(1)).update(game);
        verify(board, times(1)).confirm(0, 0);
        verify(turnManager, times(1)).endTurn();
        verify(turnManager, times(1)).startTurn();
    }
}

