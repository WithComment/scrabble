package com.example.scrabble.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

  @Test
  void testMoveCreation() {
    Letter letter = new Letter('a', 1);
    Move move = new Move(1, 2, letter);

    assertEquals(1, move.getX());
    assertEquals(2, move.getY());
    assertEquals(letter, move.getLetter());
  }
}
