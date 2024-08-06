package com.example.scrabble.use_case;

import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardOutputData;
import com.example.scrabble.use_case.get_leaderboard.GetLeaderboardInteractor;
import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetLeaderboardInteractorTest {

    @Mock
    private GameDataAccess gameDao;

    @Mock
    private Game game;

    @InjectMocks
    private GetLeaderboardInteractor getLeaderboardInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() {
        // Setup mock data
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);

        when(player1.getId()).thenReturn(1);
        when(player2.getId()).thenReturn(2);
        when(player3.getId()).thenReturn(3);

        when(player1.getScore()).thenReturn(100);
        when(player2.getScore()).thenReturn(150);
        when(player3.getScore()).thenReturn(120);

        when(game.getPlayer(1)).thenReturn(player1);
        when(game.getPlayer(2)).thenReturn(player2);
        when(game.getPlayer(3)).thenReturn(player3);

        when(gameDao.get(anyInt())).thenReturn(game);

        // Create input data with player IDs
        GetLeaderboardInputData inputData = new GetLeaderboardInputData(1, Arrays.asList(1, 2, 3));

        // Execute the interactor
        GetLeaderboardOutputData outputData = getLeaderboardInteractor.execute(inputData);

        // Verify that the leaderboard is sorted correctly
        List<Player> sortedPlayers = outputData.getLeaderboard();
        assertEquals(3, sortedPlayers.size());
        assertEquals(player2, sortedPlayers.get(0)); // Player with the highest score
        assertEquals(player3, sortedPlayers.get(1)); // Player with the second highest score
        assertEquals(player1, sortedPlayers.get(2)); // Player with the lowest score

        // Verify that the game data access update method was called
        verify(gameDao, times(1)).update(game);
    }
}

