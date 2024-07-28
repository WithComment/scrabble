package use_case.contest;

import entity.*;

import java.util.*;
import java.util.stream.Collectors;

public class ContestInteractor implements ContestInputBoundary {
    final ContestOutputBoundary contestOutputBoundary;
    final WordValidator wordValidator;

    public ContestInteractor(ContestOutputBoundary contestOutputBoundary, WordValidator wordValidator) {
        this.contestOutputBoundary = contestOutputBoundary;
        this.wordValidator = wordValidator;
    }

    private void failWithMessage(ContestInputData contestInputData, String message) {
        Game game = contestInputData.getGame();
        Player player = contestInputData.getPlayer();
        TurnManager turnManager = game.getTurnManager();
        turnManager.ContestFailureUpdate(player.getId());
        contestOutputBoundary.prepareFailedView(message);
    }

    @Override
    public void contest(ContestInputData contestInputData) {
        Game game = contestInputData.getGame();

        try {
            List<String> words = game.getLastPlay().getWords();
            List<String> invalidWords = new LinkedList<String>();
            for (String word : words) {
                if (!wordValidator.wordIsValid(word)) {
                    invalidWords.add(word);
                }
            }
            if (!invalidWords.isEmpty()) {
                Play lastPlay = game.removeLastPlay();
                Player contestedPlayer = lastPlay.getPlayer();
                contestedPlayer.BeContested();
                contestOutputBoundary.prepareSuccessView(new ContestOutputData(invalidWords));
                return;
            } else {
                failWithMessage(contestInputData, "All words in last move are valid.");
            }
        } catch (NoSuchElementException e) {
            failWithMessage(contestInputData, "No player has made any move.");
        } catch (WordValidationException e) {
            failWithMessage(contestInputData, e.getMessage());
        }
    }
}
