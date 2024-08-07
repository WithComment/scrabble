package com.example.scrabble.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

  private Board board;

  private static final Letter A = new Letter('A', 1);

  @BeforeEach
  public void setUp() {
    board = new Board();
  }
  
  @Test
  public void testGetters() {
    assertEquals(15, board.getHeight());
    assertEquals(15, board.getWidth());
  }

  @Test
  void testSetLetter() {
    board.setCell(0, 0, A);
    assertEquals(A, board.getTile(0, 0).getLetter());
  }

  @Test
  public void testConfirm() {
    board.setCell(0, 0, A);
    board.confirm(0, 0);
    assertEquals(true, board.isConfirmed(0, 0));
  }
}
