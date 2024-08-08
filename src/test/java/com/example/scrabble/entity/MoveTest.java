package com.example.scrabble.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

  @Test
  public void testMoveConstructorAndGetters() {
    Letter letter = new Letter('A', 1);
    Move move = new Move(3, 5, letter);

    assertEquals(3, move.getX());
    assertEquals(5, move.getY());
    assertEquals(letter, move.getLetter());
  }

  @Test
  public void testMoveEquality() {
    Letter letter1 = new Letter('A', 1);
    Move move1 = new Move(3, 5, letter1);

    Letter letter2 = new Letter('A', 1);
    Move move2 = new Move(3, 5, letter2);

    assertTrue(move1.equals(move2));
    assertEquals(move1, move2);

    // Test with different coordinates
    Move move3 = new Move(4, 5, letter1);
    assertNotEquals(move1, move3);

    // Test with different letters
    Letter letter3 = new Letter('B', 3);
    Move move4 = new Move(3, 5, letter3);
    assertNotEquals(move1, move4);
  }

  @Test
  public void testMoveNotEqualsWithDifferentClass() {
    Letter letter = new Letter('A', 1);
    Move move = new Move(3, 5, letter);

    assertNotEquals(move, new Object());
  }

  @Test
  public void testMoveNotEqualsWithNull() {
    Letter letter = new Letter('A', 1);
    Move move = new Move(3, 5, letter);

    assertNotEquals(move, null);
  }
}

