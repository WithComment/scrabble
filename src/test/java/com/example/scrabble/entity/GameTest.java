package com.example.scrabble.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

  private Game game;
  private Player player1;
  private Player player2;

  @BeforeEach
  void setUp() {
    List<String> playerNames = new ArrayList<>();
    playerNames.add("Player1");
    playerNames.add("Player2");
    game = new Game(playerNames);
    player1 = game.getPlayer(0);
    player2 = game.getPlayer(1);
  }

  @Test
  void testAddPlayer() {
    game.addPlayer("Player3");
    assertEquals(3, game.getNumPlayers());
  }

  @Test
  void testStartGame() {
    game.startGame();
    assertNotNull(player1.getInventory());
    assertNotNull(player2.getInventory());
    assertEquals(7, player1.getInventory().size());
    assertEquals(7, player2.getInventory().size());
  }


  @Test
  void testGetLeaderboard() {
    List<Player> leaderboard = game.getLeaderboard();
    assertEquals(2, leaderboard.size());
    assertEquals(player1, leaderboard.get(0));
    assertEquals(player2, leaderboard.get(1));
  }

  @Test
  void testContestFailureUpdate() {
    game.contestFailureUpdate(0);
    assertEquals(1, game.getPlayersNumContestFailed(0));
  }


  @Test
  void testEquals() {
    Game anotherGame = new Game(List.of("Player1", "Player2"));
    assertEquals(game.getPlayers().get(0).getName(), anotherGame.getPlayers().get(0).getName());
  }


  @Test
  public void testGameInitialization() {
    assertNotNull(game.getLetterBag());
    assertNotNull(game.getBoard());
    assertNotNull(game.getPlayers());
    assertEquals(0, game.getHistory().size());
    assertEquals(2, game.getNumPlayers());
    assertFalse(game.isEndTurn());
  }


  @Test
  public void testAddPlayerByName() {
    String playerName = "Player1";
    Player player = game.addPlayer(playerName);
    assertEquals(playerName, player.getName());
    assertEquals(3, game.getNumPlayers());
    assertEquals(7, player.getInventory().size()); // Ensure player receives 7 letters
  }


  @Test
  public void testAddPlay() {
    Play play = new Play(game.getPlayer(0));
    game.addPlay(play);
    assertEquals(1, game.getHistory().size());
    assertEquals(play, game.getHistory().get(0));
  }

  @Test
  public void testRemoveLastPlay() {
    Play play1 = new Play(game.getPlayer(0));
    Play play2 = new Play(game.getPlayer(0));
    game.addPlay(play1);
    game.addPlay(play2);
    Play removedPlay = game.removeLastPlay();
    assertEquals(play2, removedPlay);
    assertEquals(1, game.getHistory().size());
  }

  @Test
  public void testDealContest() {
    game.addPlayer("1");
    game.addPlayer("2");
    game.dealContest(true);
    assertEquals(1, game.getNumContestFailed().get(0));
  }

  @Test
  public void testEndTurn() {
    game.addPlayer("1");
    game.addPlayer("2");
    game.endTurn();
    assertTrue(game.isEndTurn());
    assertEquals(1, game.getPlayerNumber());
  }

  @Test
  public void testLeaderboard() {
    game.addPlayer("1");
    game.getPlayer(0).addScore(10);
    List<Player> leaderboard = game.getLeaderboard();
    assertEquals(3, leaderboard.size());
    assertEquals(10, leaderboard.get(0).getScore());
  }

  @Test
  public void testMultiplePlayerCreation() {
    game = new Game(3);
    assertEquals(3, game.getNumPlayers());
    for (Player player : game.getPlayers()) {
      assertEquals(7, player.getInventory().size());
    }
  }

  @Test
  public void testConstructorWithPlayerNames() {
    List<String> playerNames = List.of("Alice", "Bob", "Charlie");
    game = new Game(playerNames);
    assertEquals(3, game.getNumPlayers());
    assertEquals("Alice", game.getPlayers().get(0).getName());
    assertEquals("Bob", game.getPlayers().get(1).getName());
    assertEquals("Charlie", game.getPlayers().get(2).getName());
  }

  @Test
  public void testEqualityAndHashcode() {
    Game anotherGame = new Game();
    assertNotEquals(game, anotherGame);
    assertNotEquals(game.hashCode(), anotherGame.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("Game" + game.getId(), game.toString());
  }
}

