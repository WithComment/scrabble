package com.example.scrabble.use_case.contest;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.end_turn.EndTurnInputData;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
import com.example.scrabble.use_case.remove_letter.RemoveLetterInputData;
import com.example.scrabble.use_case.remove_letter.RemoveLetterInteractor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * Handle the contest use case.
 */
@Service
public class ContestInteractor implements ContestInputBoundary {
    private final GameDataAccess gameDAO;

    /**
     * Constructs a ContestInteractor instance with the specified GameDataAccess.
     *
     * @param gameDAO the data access object for game data
     */
    @Autowired
    public ContestInteractor(GameDataAccess gameDAO) {
        this.gameDAO = gameDAO;
    }

    /**
     * Validates whether a given word is valid according to the WordsDB web service.
     *
     * @param word the word to be validated
     * @return {@code true} if the word is valid, {@code false} otherwise
     * @throws WordValidationException if there is an issue with the URL or network communication
     */
    public boolean wordIsValid(String word) throws WordValidationException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://scrabble.us.wordsdb.ws/validateDict/" + word))
                    .GET()
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(5))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body()).getBoolean("v");
        } catch (URISyntaxException e) {
            throw new WordValidationException("Wrong URL", e);
        } catch (InterruptedException e) {
            throw new WordValidationException("Connection interrupted", e);
        } catch (IOException e) {
            throw new WordValidationException("IOException", e);
        }
    }

    /**
     * Executes the contest use case.
     *
     * @param contestInputData the input data for the contest
     * @return the output data containing the list of invalid words and a boolean indicating if the turn should end
     * @throws ContestException if there is an issue during the contest execution
     */
    @Override
    public ContestOutputData execute(ContestInputData contestInputData) throws ContestException {
        int gameID = contestInputData.getGameId();
        Game game = gameDAO.get(gameID);
        Play currentPlay = game.getCurrentPlay();
        EndTurnInteractor endTurnInteractor = new EndTurnInteractor(gameDAO);
        List<String> invalidWords = new LinkedList<>();
        if (contestInputData.getIsContest()) {
            Player player = game.getPlayer(contestInputData.getPlayerId());
            List<String> words = currentPlay.getWords();
            for (String word : words) {
                if (!wordIsValid(word)) {
                    invalidWords.add(word);
                }
            }
            if (!invalidWords.isEmpty()) {
                RemoveLetterInteractor removeLetterInteractor = new RemoveLetterInteractor(gameDAO);
                Play lastPlay = game.removeLastPlay();
                for (Move move : lastPlay.getMoves()) {
                    removeLetterInteractor.execute(new RemoveLetterInputData(game.getId(), move.getX(), move.getY()));
                }
            } else {
                game.contestFailureUpdate(player.getId());
            }
            currentPlay.getPlayer().resetTempScore();
        }
        game.increaseNumContests();
        gameDAO.update(game);
        if (game.getNumContests() >= game.getNumPlayers() - 1 || contestInputData.getIsContest()) {
            endTurnInteractor.execute(new EndTurnInputData(gameID));
            return new ContestOutputData(invalidWords, true);
        } else {
            return new ContestOutputData(invalidWords, false);
        }
    }
}