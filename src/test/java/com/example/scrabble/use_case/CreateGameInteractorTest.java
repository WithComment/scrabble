package com.example.scrabble.use_case;

import com.example.scrabble.use_case.create_game.CreateGameInteractor;
import com.example.scrabble.use_case.create_game.CreateGameInputData;
import com.example.scrabble.use_case.create_game.CreateGameOutputData;
import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateGameInteractorTest {

    @Mock
    private GameDataAccess gameDao;

    @InjectMocks
    private CreateGameInteractor createGameInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() {
        List<String> playerNames = Arrays.asList("Player1", "Player2");
        CreateGameInputData inputData = new CreateGameInputData(playerNames);

        // Execute the create game interactor
        CreateGameOutputData outputData = createGameInteractor.execute(inputData);


        // Verify that a game was created and the data access was called
        verify(gameDao, times(1)).create(any(Game.class));

        // Verify the players in the output data
        assertEquals(playerNames.size(), outputData.getPlayers().size());

        // Verify the player names
        for (int i = 0; i < playerNames.size(); i++) {
            assertEquals(playerNames.get(i), outputData.getPlayers().get(i).getName());
        }
    }
}

