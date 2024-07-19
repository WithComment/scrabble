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

    private List<String> getWordsFromLastMove(ContestInputData contestInputData) {
        class Position {
            public final int x;
            public final int y;

            public Position(int x, int y) {
                this.x = x;
                this.y = y;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Position) {
                    Position pos = (Position) obj;
                    return x == pos.x && y == pos.y;
                }
                return false;
            }

            public String toString() {
                return String.format("(%d, %d)", x, y);
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }
        }

        class Word {
            public final Position start;
            public final Position end;
            public final String word;

            public Word(Position start, Position end, String word) {
                this.start = start;
                this.end = end;
                this.word = word;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Word) {
                    Word word = (Word) obj;
                    return this.start.equals(word.start) && this.end.equals(word.end) && this.word.equals(word.word);
                }
                return false;
            }

            @Override
            public String toString() {
                return this.start + " " + this.end + " " + this.word;
            }

            @Override
            public int hashCode() {
                return Objects.hash(start, end, word);
            }
        }

        Set<Word> words = new HashSet<Word>();

        Game game = contestInputData.getGame();
        Play lastPlay = game.getLastPlay();

        for (Move move : lastPlay.getMoves()) {
            final int x = move.getX(), y = move.getY();
            int xStart = x, xEnd = x, yStart = y, yEnd = y;

            for (int i = move.getX(); i >= 0 && game.getBoardCell(i, y).getLetter() != null; i--) {
                xStart = i;
            }
            for (int i = move.getX(); i < 15 && game.getBoardCell(i, y).getLetter() != null; i++) {
                xEnd = i;
            }
            for (int i = move.getY(); i >= 0 && game.getBoardCell(x, i).getLetter() != null; i--) {
                yStart = i;
            }
            for (int i = move.getY(); i < 15 && game.getBoardCell(x, i).getLetter() != null; i++) {
                yEnd = i;
            }

            if (xStart != xEnd) {
                StringBuilder word = new StringBuilder();
                for (int i = xStart; i <= xEnd; i++) {
                    word.append(game.getBoardCell(i, y).getLetter().getLetter());
                }
                words.add(new Word(new Position(xStart, y), new Position(xEnd, y), word.toString()));
            }
            if (yStart != yEnd) {
                StringBuilder word = new StringBuilder();
                for (int i = yStart; i <= yEnd; i++) {
                    word.append(game.getBoardCell(x, i).getLetter().getLetter());
                }
                words.add(new Word(new Position(x, yStart), new Position(x, yEnd), word.toString()));
            }
        }

        return words.stream().map(word -> word.word).collect(Collectors.toList());
    }

    @Override
    public void contest(ContestInputData contestInputData) {
        Game game = contestInputData.getGame();

        try {
            List<String> words = getWordsFromLastMove(contestInputData);
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
