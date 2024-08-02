package com.example.scrabble.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class LetterTest {
  @Test
  void testClone() {
    Letter a = new Letter('a', 1);
    Letter b = a.clone();
    assertEquals(a.getLetter(), b.getLetter());
    assertEquals(a.getPoints(), b.getPoints());
  }

  @Test
  void testEquals() {
    Letter a = new Letter('a', 1);
    Letter a2 = a.clone();
    Letter a3 = new Letter('b', 1);
    assertEquals(a, a2);
    assertNotEquals(a, a3);
  }

  @Test
  void testGetLetter() {
    Letter a = new Letter('a', 1);
    assertEquals('a', a.getLetter());
  }

  @Test
  void testGetPoints() {
    Letter a = new Letter('a', 1);
    assertEquals(1, a.getPoints());
  }

  @Test
  void testToString() {
    Letter a = new Letter('a', 1);
    assertEquals("a", a.toString());
  }
}
