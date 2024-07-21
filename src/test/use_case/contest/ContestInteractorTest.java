package test.use_case.contest;

import entity.*;
import org.junit.jupiter.api.Test;
import use_case.contest.ContestInputData;
import use_case.contest.ContestInteractor;
import use_case.contest.ContestOutputBoundary;
import use_case.contest.ContestOutputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContestInteractorTest {
    private void placeLetter(Game game, int x, int y, Letter letter) {
        Tile tile = game.getBoardCell(x, y);
        game.setBoardCell(x, y, new Tile(1, 1, letter));
    }

    @Test
    void testInteractorFail() {
        ContestOutputBoundary successPresenter = new ContestOutputBoundary() {
            @Override
            public void prepareSuccessView(ContestOutputData outputData) {
                fail("Should not have successed");
            }

            @Override
            public void prepareFailedView(String error) {
                assertEquals("All words in last move are valid.", error);
            }
        };

        Game game = new Game(new LinkedList<>());
        Player a = game.addPlayer(), b = game.addPlayer();
        placeLetter(game, 7, 7, new Letter('P', 1));
        placeLetter(game, 7, 8, new Letter('L', 1));
        placeLetter(game, 7, 9, new Letter('A', 1));
        placeLetter(game, 7, 10, new Letter('Y', 1));
        Play play = new Play(a);
        play.addMove(new Move(7, 7, new Letter('P', 1)));
        play.addMove(new Move(7, 8, new Letter('L', 1)));
        play.addMove(new Move(7, 9, new Letter('A', 1)));
        play.addMove(new Move(7, 10, new Letter('Y', 1)));
        ArrayList<String> words = new ArrayList<>();
        words.add("PLAY");
        play.setWords(words);
        game.addPlay(play);

        ContestInputData inputData = new ContestInputData(game, b);
        WordValidator successWordValidator = new WordValidator() {
            @Override
            public boolean wordIsValid(String word) throws WordValidationException {
                assertEquals("PLAY", word);
                return true;
            }
        };
        ContestInteractor interactor = new ContestInteractor(successPresenter, successWordValidator);
        interactor.contest(inputData);
    }

    @Test
    void testInteractorSuccess() {
        ContestOutputBoundary successPresenter = new ContestOutputBoundary() {
            @Override
            public void prepareSuccessView(ContestOutputData outputData) {
                List<String> invalidWords = outputData.getInvalidWords();
                assertIterableEquals(Arrays.asList("PLA"), invalidWords);
            }

            @Override
            public void prepareFailedView(String error) {
                fail("Should not have failed");
            }
        };

        Game game = new Game(new LinkedList<>());
        Player a = game.addPlayer(), b = game.addPlayer();
        placeLetter(game, 7, 7, new Letter('P', 1));
        placeLetter(game, 7, 8, new Letter('L', 1));
        placeLetter(game, 7, 9, new Letter('A', 1));
        Play play = new Play(a);
        play.addMove(new Move(7, 7, new Letter('P', 1)));
        play.addMove(new Move(7, 8, new Letter('L', 1)));
        play.addMove(new Move(7, 9, new Letter('A', 1)));
        play.setWords(Arrays.asList("PLA"));
        game.addPlay(play);

        ContestInputData inputData = new ContestInputData(game, b);
        WordValidator successWordValidator = new WordValidator() {
            @Override
            public boolean wordIsValid(String word) throws WordValidationException {
                assertEquals("PLA", word);
                return false;
            }
        };
        ContestInteractor interactor = new ContestInteractor(successPresenter, successWordValidator);
        interactor.contest(inputData);
    }
}