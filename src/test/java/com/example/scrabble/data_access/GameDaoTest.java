package com.example.scrabble.data_access;

import com.example.scrabble.entity.Game;
import com.example.scrabble.entity.Player;
import com.example.scrabble.entity.Tile;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GameDaoTest {

    @InjectMocks
    private GameDao gameDao; // The class under test

    @Test
    public void testCreateSuccess() throws Exception {
        List<Player> players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());
        players.add(new Player());
        players.add(new Player());

        Game game = new Game();
        game.setPlayers(players);
        Game createdGame = gameDao.create(game);

        int gameId = game.getId();
        Game fetchedGame = gameDao.get(gameId);

        Assertions.assertEquals(createdGame, game);
        Assertions.assertEquals(createdGame, fetchedGame);
    }
}

