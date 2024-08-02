package com.example.scrabble.use_case;

import com.example.scrabble.data_access.GameDataAccess;
import com.example.scrabble.entity.*;
import com.example.scrabble.use_case.contest.ContestException;
import com.example.scrabble.use_case.contest.ContestInputData;
import com.example.scrabble.use_case.contest.ContestInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ContestInteractorTest {
    private GameDataAccess gameDAO;
    private ContestInteractor interactor;

    @BeforeEach
    public void setUp() {
        gameDAO = new GameDataAccess() {
            private final List<Game> games = new LinkedList<>();

            @Override
            public Game create(Game game) {
                games.add(game);
                return game;
            }

            @Override
            public Game get(int gameId) {
                for (Game game: games) {
                    if (game.getId() == gameId) {
                        return game;
                    }
                }
                return null;
            }

            @Override
            public void update(Game game) {
                for (int i=0; i<games.size(); i++){
                    if (games.get(i).getId() == game.getId()) {
                        games.set(i, game);
                        return;
                    }
                }
            }

            @Override
            public void delete(int gameId) {
                for (int i=0; i<games.size(); i++) {
                    if (games.get(i).getId() == gameId) {
                        games.remove(i);
                        return;
                    }
                }
            }
        };
        interactor = new ContestInteractor(gameDAO);
    }

    @Test
    public void testWordIsValid() {
        try {
            Player alice = new Player("Alice"), bob = new Player("Bob");
            Game game = new Game();
            game.setPlayers(List.of(alice, bob));
            gameDAO.create(game);
            game.startGame();
            List<Letter> aliceInventory = alice.getInventory();
            aliceInventory.clear();
            aliceInventory.addAll(List.of(
                new Letter('h', 4),
                new Letter('e', 1),
                new Letter('l', 1),
                new Letter('l', 1),
                new Letter('o', 1)
            ));
            game.getTurnManager().startTurn();
            Play currentPlay = game.getCurrentPlay();
            Board board = game.getBoard();
            currentPlay.addMove(new Move(7, 7, aliceInventory.get(0)));
            board.setCell(7, 7, aliceInventory.get(0));
            currentPlay.addMove(new Move(7, 8, aliceInventory.get(1)));
            board.setCell(7, 8, aliceInventory.get(1));
            currentPlay.addMove(new Move(7, 9, aliceInventory.get(2)));
            board.setCell(7, 9, aliceInventory.get(2));
            currentPlay.addMove(new Move(7, 10, aliceInventory.get(3)));
            board.setCell(7, 10, aliceInventory.get(3));
            currentPlay.addMove(new Move(7, 11, aliceInventory.get(4)));
            board.setCell(7, 11, aliceInventory.get(4));
            aliceInventory.clear();
            currentPlay.setWords(List.of("hello"));

            ContestInputData contestInputData = new ContestInputData(game.getId(), bob.getId());
            ContestException exception = assertThrows(ContestException.class, () -> interactor.execute(contestInputData));
            assert(exception.getMessage().contains("All words in last move are valid."));
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}
