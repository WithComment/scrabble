package com.example.scrabble.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TileTest {
  private Tile tile;
  private Letter mockLetter;

  @BeforeEach
  void setUp() {
    // Initialize mock Letter object and Tile instance before each test
    mockLetter = mock(Letter.class);
    tile = new Tile(2, 3, mockLetter);
  }

  @Test
  void testConstructorWithParams() {
    assertNotNull(tile);
    assertEquals(2, tile.getWordMult());
    assertEquals(3, tile.getLetterMult());
    assertEquals(mockLetter, tile.getLetter());
    assertFalse(tile.isConfirmed());
  }

//  @Test
//  void testConstructorWithJson() {
//    JSONObject json = new JSONObject()
//            .put("wordMult", 2)
//            .put("letterMult", 3)
//            .put("letter", new JSONObject().put("someKey", "someValue")) // Mock Letter JSON representation
//            .put("isConfirmed", false);
//
//    tile = new Tile(json);
//
//    assertEquals(2, tile.getWordMult());
//    assertEquals(3, tile.getLetterMult());
//    assertNotNull(tile.getLetter()); // Check if the Letter is correctly parsed
//    assertFalse(tile.isConfirmed());
//  }

  @Test
  void testSetLetter() {
    Letter newLetter = mock(Letter.class);
    tile.setLetter(newLetter);

    assertEquals(newLetter, tile.getLetter());
  }

  @Test
  void testRemoveLetter() {
    tile.removeLetter();

    assertNull(tile.getLetter());
    assertFalse(tile.isConfirmed());
  }

  @Test
  void testSetConfirmed() {
    tile.setConfirmed(true);

    assertTrue(tile.isConfirmed());
  }

  @Test
  void testConfirm() {
    tile.confirm();

    assertTrue(tile.isConfirmed());
  }

  @Test
  void testIsEmpty() {
    assertFalse(tile.isEmpty());

    tile.removeLetter();
    assertTrue(tile.isEmpty());
  }

  @Test
  void testToString() {
    when(mockLetter.toString()).thenReturn("A");
    assertEquals("A", tile.toString());

    tile.removeLetter();
    assertEquals(" ", tile.toString());
  }

  @Test
  void testEquals() {
    Tile anotherTile = new Tile(2, 3, mockLetter);
    assertEquals(tile, anotherTile);

    Tile differentTile = new Tile(1, 2, null);
    assertNotEquals(tile, differentTile);
  }
}

