package com.example.scrabble.use_case;

import com.example.scrabble.use_case.get_winner.GetWinnerInteractor;
import com.example.scrabble.use_case.get_winner.GetWinnerOutputData;
import com.example.scrabble.use_case.get_winner.GetWinnerInputData;
import com.example.scrabble.use_case.get_winner.GetWinnerOutputBoundary;
import com.example.scrabble.entity.LeaderboardEntry;
import com.example.scrabble.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetWinnerInteractorTest {

    @Mock
    private GetWinnerOutputBoundary presenter;

    @InjectMocks
    private GetWinnerInteractor getWinnerInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_singleWinner() {
        // Setup mock players with different scores
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);

        when(player1.getScore()).thenReturn(100);
        when(player2.getScore()).thenReturn(150); // Highest score
        when(player3.getScore()).thenReturn(120);

        when(player1.getId()).thenReturn(1);
        when(player2.getId()).thenReturn(2);
        when(player3.getId()).thenReturn(3);

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3));

        GetWinnerInputData inputData = new GetWinnerInputData(players);

        // Execute the interactor
        getWinnerInteractor.execute(inputData);

        // Capture the output data passed to the presenter
        ArgumentCaptor<GetWinnerOutputData> captor = ArgumentCaptor.forClass(GetWinnerOutputData.class);
        verify(presenter).prepareView(captor.capture());

        GetWinnerOutputData outputData = captor.getValue();
        List<LeaderboardEntry> winners = outputData.getWinner();

        // Verify that only one winner is identified
        assertEquals(1, winners.size());
        assertEquals(150, winners.get(0).getScore());
    }

    @Test
    void testExecute_tieBetweenTwoPlayers() {
        // Setup mock players with two tying for the highest score
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);

        when(player1.getScore()).thenReturn(150); // Tie for the highest score
        when(player2.getScore()).thenReturn(150); // Tie for the highest score
        when(player3.getScore()).thenReturn(120);

        when(player1.getId()).thenReturn(1);
        when(player2.getId()).thenReturn(2);
        when(player3.getId()).thenReturn(3);

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3));

        GetWinnerInputData inputData = new GetWinnerInputData(players);

        // Execute the interactor
        getWinnerInteractor.execute(inputData);

        // Capture the output data passed to the presenter
        ArgumentCaptor<GetWinnerOutputData> captor = ArgumentCaptor.forClass(GetWinnerOutputData.class);
        verify(presenter).prepareView(captor.capture());

        GetWinnerOutputData outputData = captor.getValue();
        List<LeaderboardEntry> winners = outputData.getWinner();

        // Verify that two winners are identified due to tie
        assertEquals(2, winners.size());
        assertEquals(150, winners.get(0).getScore());
        assertEquals(150, winners.get(1).getScore());
    }

    @Test
    void testExecute_allPlayersTie() {
        // Setup mock players all tying for the highest score
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);

        when(player1.getScore()).thenReturn(150); // All have the same score
        when(player2.getScore()).thenReturn(150);
        when(player3.getScore()).thenReturn(150);

        when(player1.getId()).thenReturn(1);
        when(player2.getId()).thenReturn(2);
        when(player3.getId()).thenReturn(3);

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3));

        GetWinnerInputData inputData = new GetWinnerInputData(players);

        // Execute the interactor
        getWinnerInteractor.execute(inputData);

        // Capture the output data passed to the presenter
        ArgumentCaptor<GetWinnerOutputData> captor = ArgumentCaptor.forClass(GetWinnerOutputData.class);
        verify(presenter).prepareView(captor.capture());

        GetWinnerOutputData outputData = captor.getValue();
        List<LeaderboardEntry> winners = outputData.getWinner();

        // Verify that all players are identified as winners due to a three-way tie
        assertEquals(3, winners.size());
        assertEquals(150, winners.get(0).getScore());
        assertEquals(150, winners.get(1).getScore());
        assertEquals(150, winners.get(2).getScore());
    }
}

