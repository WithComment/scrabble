package com.example.scrabble.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PlayTest {

  private static Letter a = new Letter('a', 1);
  private static Letter b = new Letter('a', 1);
  private static Player player = new Player("John");

  @Test
  void testPlayCreation() {
    Play play = new Play(player);

    assertEquals(player, play.getPlayer());
    assertTrue(play.getMoves().isEmpty());
  }

  @Test
  void testAddMove() {
    Play play = new Play(player);
    Move move = new Move(1, 2, a);

    play.addMove(move);
    assertEquals(1, play.getMoves().size());
    assertEquals(move, play.getMoves().get(0));
  }

  @Test
  void testUndoMove() {
    Play play = new Play(player);
    Move move1 = new Move(1, 2, a);
    Move move2 = new Move(3, 4, b);

    play.addMove(move1);
    play.addMove(move2);

    Move undoneMove = play.removeMove();
    assertEquals(move2, undoneMove);
    assertEquals(1, play.getMoves().size());
    assertEquals(move1, play.getMoves().get(0));
  }

  @Test
  void testRemoveMove() {
    Play play = new Play(player);
    Move move1 = new Move(1, 2, a);
    Move move2 = new Move(3, 4, b);

    play.addMove(move1);
    play.addMove(move2);

    Move removedMove = play.removeMove(1, 2);
    assertEquals(move1, removedMove);
    assertEquals(1, play.getMoves().size());
    assertEquals(move2, play.getMoves().get(0));

    removedMove = play.removeMove(1, 2);
    assertNull(removedMove);
  }

  @Test
  void testSetAndGetWords() {
    Player player = new Player("John");
    Play play = new Play(player);
    List<String> words = new ArrayList<>();
    words.add("TEST");
    words.add("WORD");

    play.setWords(words);
    assertEquals(words, play.getWords());
  }
}
