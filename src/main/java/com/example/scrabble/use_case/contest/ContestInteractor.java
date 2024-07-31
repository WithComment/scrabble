package com.example.scrabble.use_case.contest;


import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
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
import java.util.NoSuchElementException;

@Service
public class ContestInteractor implements ContestInputBoundary {
    private final GameDataAccess gameDAO;
    private Game game;

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
    private boolean wordIsValid(String word) throws WordValidationException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://scrabble.us.wordsdb.ws/validateDict/" + word))
                    .GET()
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(5))
                    .build();
            HttpResponse<String> response= HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body()).getBoolean("v");
        } catch (URISyntaxException e) {
            throw new WordValidationException("Wrong URL", e);
        } catch (InterruptedException e) {
            throw new WordValidationException("Connection interrupted", e);
        } catch (IOException e) {
            throw new WordValidationException("IOException", e);
        }
    }

    private void failWithMessage(ContestInputData contestInputData, String message) {
        Player player = game.getPlayer(contestInputData.getPlayerId());
        TurnManager turnManager = game.getTurnManager();
        turnManager.ContestFailureUpdate(player.getId());
    }

    @Override
    public Game execute(ContestInputData contestInputData) throws IOException, ClassNotFoundException {
        game = gameDAO.get(contestInputData.getGameId());

        try {
            List<String> words = game.getLastPlay().getWords();
            List<String> invalidWords = new LinkedList<>();
            for (String word : words) {
                if (!wordIsValid(word)) {
                    invalidWords.add(word);
                }
            }
            if (!invalidWords.isEmpty()) {
                Play lastPlay = game.removeLastPlay();
                Player contestedPlayer = lastPlay.getPlayer();
                contestedPlayer.BeContested();
            } else {
                failWithMessage(contestInputData, "All words in last move are valid.");
            }
        } catch (NoSuchElementException e) {
            failWithMessage(contestInputData, "No player has made any move.");
        } catch (WordValidationException e) {
            failWithMessage(contestInputData, e.getMessage());
        }
        return game;
    }
}
