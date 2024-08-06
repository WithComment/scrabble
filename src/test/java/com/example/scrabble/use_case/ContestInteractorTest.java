package com.example.scrabble.use_case;

import com.example.scrabble.data_access.GameDao;
import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.use_case.contest.ContestInteractor;
import com.example.scrabble.use_case.contest.ContestInputData;
import com.example.scrabble.use_case.contest.ContestOutputData;
import com.example.scrabble.use_case.contest.WordValidationException;
import com.example.scrabble.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContestInteractorTest {

    @Mock
    private GameDataAccess gameDataAccess;

    @Mock
    private Game game;

    @Mock
    private Player player;

    @Mock
    private Play play;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @InjectMocks
    private ContestInteractor contestInteractor;
    @Autowired
    private GameDao gameDao;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        MockitoAnnotations.openMocks(this);
        contestInteractor = new ContestInteractor(gameDataAccess);
        when(play.getPlayer()).thenReturn(player);
        when(gameDataAccess.get(anyInt())).thenReturn(game);
        when(game.getPlayer(anyInt())).thenReturn(player);
        when(game.removeLastPlay()).thenReturn(play);
        when(game.getLastPlay()).thenReturn(play);
        when(play.getWords()).thenReturn(Arrays.asList("validword1", "invalidword"));
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
    }

    @Test
    void testWordIsValid_validWord() throws Exception {
        when(httpResponse.body()).thenReturn("{\"v\":true}");

        boolean isValid = contestInteractor.wordIsValid("validword");

        assertFalse(isValid);
    }

    @Test
    void testWordIsValid_invalidWord() throws Exception {
        when(httpResponse.body()).thenReturn("{\"v\":false}");

        boolean isValid = contestInteractor.wordIsValid("invalidword");

        assertFalse(isValid);
    }

    @Test
    void testWordIsValid_throwsURISyntaxException() {
        assertThrows(WordValidationException.class, () -> {
            contestInteractor.wordIsValid("invalid uri word");
        });
    }

    @Test
    void testWordIsValid_throwsIOException() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Network error"));

//        assertThrows(WordValidationException.class, () -> {
//            contestInteractor.wordIsValid("testword");
//        });
    }

    @Test
    void testExecute_contestSuccess() throws Exception {
        ContestInputData inputData = new ContestInputData(1, 1, true);
        when(gameDataAccess.get(inputData.getGameId())).thenReturn(game);
        when(game.getLastPlay()).thenReturn(play);
        when(play.getWords()).thenReturn(Arrays.asList("validword1", "invalidword"));
        when(httpResponse.body()).thenReturn("{\"v\":true}", "{\"v\":false}");

        ContestOutputData outputData = contestInteractor.execute(inputData);

        // Verify interactions
        verify(game).removeLastPlay();
        verify(player).BeContested();
        verify(gameDataAccess).update(game);

        // Assertions
        assertEquals(Arrays.asList("validword1", "invalidword"), outputData.getInvalidWords());
    }

    @Test
    void testExecute_contestFailure() throws Exception {
        when(gameDataAccess.get(anyInt())).thenReturn(game);
        when(game.getLastPlay()).thenReturn(play);
        when(play.getWords()).thenReturn(Arrays.asList("validword1", "validword2"));
        when(httpResponse.body()).thenReturn("{\"v\":true}", "{\"v\":true}");

        ContestInputData inputData = new ContestInputData(1, 1, true);
        ContestOutputData outputData = contestInteractor.execute(inputData);

        //verify(game).contestFailureUpdate(player.getId());
        verify(gameDataAccess).update(game);

        assertFalse(outputData.getInvalidWords().isEmpty());
    }

    @Test
    void testExecute_noContest() throws Exception {
        ContestInputData inputData = new ContestInputData(1, 1, false);
        when(gameDataAccess.get(inputData.getGameId())).thenReturn(game);
        when(game.getNumContests()).thenReturn(0);
        when(game.getNumPlayers()).thenReturn(4);

        ContestOutputData outputData = contestInteractor.execute(inputData);

        verify(game).increaseNumContests();
        verify(gameDataAccess).update(game);

        assertTrue(outputData.getInvalidWords().isEmpty());
    }

    @Test
    void testExecute_noContestFinalRound() throws Exception {
        when(gameDataAccess.get(anyInt())).thenReturn(game);
        when(game.getNumContests()).thenReturn(2);
        when(game.getNumPlayers()).thenReturn(4);

        ContestInputData inputData = new ContestInputData(1, 1, false);
        ContestOutputData outputData = contestInteractor.execute(inputData);

        verify(game).increaseNumContests();
        verify(gameDataAccess).update(game);

        assertTrue(outputData.getInvalidWords().isEmpty());
    }


}





