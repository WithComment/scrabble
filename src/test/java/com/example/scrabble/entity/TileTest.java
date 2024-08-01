package com.example.scrabble.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TileTest {

  private static Letter a = new Letter('a', 1);

  @Test
  void testConfirm() {
    Tile t = new Tile(1, 1, a);
    assertFalse(t.isConfirmed());
    t.confirm();
    assertTrue(t.isConfirmed());
  }

  @Test
  void testEquals() {
    Tile t = new Tile(1, 1, a);
    Tile t2 = new Tile(1, 1, a.clone());
    Tile t3 = new Tile(2, 1, new Letter('b', 1));
    assertEquals(t, t2);
    assertNotEquals(t, t3);
  }

  @Test
  void testGetLetter() {
    Tile t = new Tile(1, 1, a);
    assertEquals(a, t.getLetter());
  }

  @Test
  void testGetLetterMult() {
    Tile t = new Tile(1, 2, a);
    assertEquals(2, t.getLetterMult());
  }

  @Test
  void testGetWordMult() {
    Tile t = new Tile(2, 1, a);
    assertEquals(2, t.getWordMult());
  }

  @Test
  void testIsEmpty() {
    Tile t = new Tile(1, 1, a);
    Tile t2 = new Tile(1, 1, null);
    assertFalse(t.isEmpty());
    assertTrue(t2.isEmpty());
  }

  @Test
  void testRemoveLetter() {
    Tile t = new Tile(1, 1, a);
    t.removeLetter();
    assertTrue(t.isEmpty());
  }

  @Test
  void testSetLetter() {
    Tile t = new Tile(1, 1, null);
    t.setLetter(a);
    assertEquals(a, t.getLetter());
  }

  @Test
  void testToString() {
    Tile t = new Tile(1, 1, a);
    assertEquals("a", t.toString());
    t.removeLetter();
    assertEquals(" ", t.toString());
  }
}
