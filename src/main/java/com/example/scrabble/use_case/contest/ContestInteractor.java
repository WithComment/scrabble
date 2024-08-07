package com.example.scrabble.use_case.contest;


import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.end_turn.EndTurnInputBoundary;
import com.example.scrabble.use_case.end_turn.EndTurnInputData;
import com.example.scrabble.use_case.end_turn.EndTurnInteractor;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContestInteractor implements ContestInputBoundary {
    private final GameDataAccess gameDAO;
    private Game game;
    private Player player;

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

    public void fail() {
        game.contestFailureUpdate(player.getId());
    }

    @Override
    public ContestOutputData execute(ContestInputData contestInputData) throws ContestException {
        game = gameDAO.get(contestInputData.getGameId());
        if (game == null) {
            throw new ContestException("Game not found");
        }
        if (contestInputData.getIsContest()) {
            player = game.getPlayer(contestInputData.getPlayerId());
            List<String> words = game.getCurrentPlay().getWords();
            List<String> invalidWords = new LinkedList<>();

            //try {
                for (String word : words) {
                    if (!wordIsValid(word)) {
                        invalidWords.add(word);
                    }
                }
                if (!invalidWords.isEmpty()) {
                    Play lastPlay = game.removeLastPlay();
                    Player contestedPlayer = lastPlay.getPlayer();
                    contestedPlayer.resetTempScore();
                } else {
                    fail();
//                    throw new ContestException("All words in last move are valid.");
                }
//            } catch (WordValidationException e) {
//                fail();
//                throw new ContestException("Word validation failed.", e);
//            }
            gameDAO.update(game);
            EndTurnInteractor endTurnInteractor = new EndTurnInteractor(gameDAO);
            endTurnInteractor.execute(new EndTurnInputData(game.getId()));
            return new ContestOutputData(invalidWords, true);
        }
        game.increaseNumContests();
        gameDAO.update(game);
        if (game.getNumContests() >= game.getNumPlayers() - 1) {
            EndTurnInteractor endTurnInteractor = new EndTurnInteractor(gameDAO);
            endTurnInteractor.execute(new EndTurnInputData(game.getId()));
            game = gameDAO.get(game.getId());
            return new ContestOutputData(new ArrayList<>(), true);
        } else {
            return new ContestOutputData(new ArrayList<>(), false);
        }
    }
}
