package com.example.scrabble.data_access;

import com.example.scrabble.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GameDaoTest {
    @Mock
    private Letter letter;

    @InjectMocks
    private GameDao gameDao; // The class under test

    @Test
    public void testCreateSuccess() throws Exception {
        Game game = new Game(4);
        Game createdGame = gameDao.create(game);

        int gameId = game.getId();
        Game fetchedGame = gameDao.get(gameId);

        Assertions.assertEquals(createdGame, game);
        Assertions.assertEquals(createdGame, fetchedGame);
    }
}

