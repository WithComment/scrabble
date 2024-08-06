package com.example.scrabble.data_access;

import com.example.scrabble.entity.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameDatabaseTest {
    @Autowired
    private GameDatabase gameDatabase; // The class under test
//
//    @BeforeEach
//    public void setUp() {
//        gameDatabase.gameRepository.deleteAll();
//    }

    @Test
    public void testCreateSuccess() {
        Game game = new Game(4);
        Game createdGame = gameDatabase.create(game);

        int gameId = game.getId();
        Game fetchedGame = gameDatabase.get(gameId);

        Assertions.assertEquals(createdGame, game);
        Assertions.assertEquals(createdGame, fetchedGame);
    }
}

