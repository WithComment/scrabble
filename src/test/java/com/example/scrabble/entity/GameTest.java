package com.example.scrabble.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
  
  private Game game;

  @BeforeEach
  void setUp() {
    game = new Game();
  }

  @Test
  void testAddPlayer() {
    game.addPlayer("1");
    game.addPlayer("2");
    assertEquals(2, game.getPlayers().size());
    game.addPlayer("3");
    assertEquals(3, game.getPlayers().size());
  }

  @Test
  void testGetLeaderboard() {
    game.addPlayer("1");
    game.addPlayer("2");
    game.addPlayer("2");
    game.getPlayer(0).setTempScore(100);
    game.getPlayer(1).setTempScore(150);
    game.getPlayer(2).setTempScore(120);
    game.getPlayer(0).confirmTempScore();
    game.getPlayer(1).confirmTempScore();
    game.getPlayer(2).confirmTempScore();
    List<Player> leaderboard = game.getLeaderboard();
    assertEquals(3, leaderboard.size());
    assertEquals(150, leaderboard.get(0).getScore());
    assertEquals(120, leaderboard.get(1).getScore());
    assertEquals(100, leaderboard.get(2).getScore());
  }
}
