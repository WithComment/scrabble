package com.example.scrabble.data_access;

import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Letter;
import com.example.scrabble.entity.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GameDatabaseTest {
    @Autowired
    private GameDatabase gameDatabase; // The class under test

    @BeforeEach
    public void setUp() {
        gameDatabase.deleteAll();
    }

    @Test
    public void testCreateSuccess() {
        Game game = new Game(4);
        Game createdGame = gameDatabase.create(game);

        int gameId = game.getId();
        Game fetchedGame = gameDatabase.get(gameId);

        assertEquals(createdGame, game);
        assertEquals(createdGame, fetchedGame);
    }

    @Test
    public void testUpdateSuccess() {
        Game game = new Game(4);
        Game createdGame = gameDatabase.create(game);

        game.startGame();
        gameDatabase.update(game);
        game.getCurrentPlay().addMove(new Move(0, 0, new Letter('A', 1)));
        gameDatabase.update(game);
        int gameId = game.getId();
        Game fetchedGame = gameDatabase.get(gameId);

        assertEquals(createdGame, game);
        assertEquals(createdGame, fetchedGame);
        assertEquals(game.getCurrentPlay().getMoves(), fetchedGame.getCurrentPlay().getMoves());
    }
}

