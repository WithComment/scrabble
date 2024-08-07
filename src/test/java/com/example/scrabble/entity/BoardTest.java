package com.example.scrabble.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

  private Board board;

  @BeforeEach
  public void setUp() {
    board = new Board();
  }

  @Test
  public void testBoardInitialization() {
    // Test the board is initialized correctly with the default configuration
    assertNotNull(board.getBoard());
    assertEquals(15, board.getHeight());
    assertEquals(15, board.getWidth());
  }

  @Test
  public void testGetCell() {
    // Test getting a tile from the board
    Tile tile = board.getCell(0, 0);
    assertNotNull(tile);
  }

  @Test
  public void testSetCell() {
    // Test setting a tile on the board
    Tile newTile = new Tile(2, 1, null);
    board.setCell(0, 0, newTile);
    assertEquals(newTile, board.getCell(0, 0));
  }

  @Test
  public void testSetCellWithLetter() {
    // Test setting a letter on a tile
    Letter letter = new Letter('A', 1);
    board.setCell(0, 0, letter);
    assertEquals(letter, board.getCell(0, 0).getLetter());
  }

  @Test
  public void testConfirm() {
    // Test confirming a tile
    board.setCell(0,0, new Letter('A', 1));
    assertTrue(board.confirm(0, 0));
    assertTrue(board.isConfirmed(0, 0));
  }

  @Test
  public void testToString() {
    // Test the string representation of the board
    String boardStr = board.toString();
    assertNotNull(boardStr);
    assertTrue(boardStr.length() > 0);
  }

  @Test
  public void testMultiplierString() {
    // Test the string representation of the multipliers on the board
    String multiplierStr = board.multiplierString();
    assertNotNull(multiplierStr);
    assertTrue(multiplierStr.length() > 0);
  }

  @Test
  public void testEquals() {
    // Test the equals method for comparing boards
    Board otherBoard = new Board();
    assertTrue(board.equals(otherBoard));

    otherBoard.setCell(0, 0, new Tile(2, 1, null));
    assertFalse(board.equals(otherBoard));
  }

//  @Test
//  public void testBoardConstructorWithJsonObject() {
//    // Create a JSONObject representing the board state
//    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("height", 15);
//    jsonObject.put("width", 15);
//    JSONObject boardObject = new JSONObject();
//    for (int i = 0; i < 15; i++) {
//      for (int j = 0; j < 15; j++) {
//        JSONObject tileObject = new JSONObject();
//        tileObject.put("wordMult", 1);
//        tileObject.put("letterMult", 1);
//        boardObject.put(i + "," + j, tileObject);
//      }
//    }
//    jsonObject.put("board", boardObject);
//
//    // Initialize the board using the JSONObject constructor
//    Board boardFromJson = new Board(jsonObject);
//
//    // Check that the board has been correctly initialized
//    assertEquals(15, boardFromJson.getHeight());
//    assertEquals(15, boardFromJson.getWidth());
//    assertEquals(board.getCell(0, 0), boardFromJson.getCell(0, 0));
//  }
}
