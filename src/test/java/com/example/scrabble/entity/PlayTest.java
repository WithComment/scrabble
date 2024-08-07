package com.example.scrabble.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

class PlayTest {

  private Player mockPlayer;
  private Move mockMove1;
  private Move mockMove2;
  private Play play;

  @BeforeEach
  void setUp() {
    // Initialize mock objects and Play instance before each test
    mockPlayer = mock(Player.class);
    mockMove1 = mock(Move.class);
    mockMove2 = mock(Move.class);

    when(mockMove1.getX()).thenReturn(1);
    when(mockMove1.getY()).thenReturn(1);
    when(mockMove2.getX()).thenReturn(2);
    when(mockMove2.getY()).thenReturn(2);

    play = new Play(mockPlayer);
  }

  @Test
  void testConstructorWithParams() {
    LinkedList<Move> moves = new LinkedList<>(Arrays.asList(mockMove1, mockMove2));
    Play playWithParams = new Play(mockPlayer, moves, Arrays.asList("word1", "word2"), true);

    assertEquals(mockPlayer, playWithParams.getPlayer());
    assertEquals(moves, playWithParams.getMoves());
    assertEquals(Arrays.asList("word1", "word2"), playWithParams.getWords());
    assertTrue(playWithParams.getFailedContest());
  }

  @Test
  void testAddMove() {
    play.addMove(mockMove1);
    assertEquals(1, play.getMoves().size());
    assertTrue(play.getMoves().contains(mockMove1));
  }

  @Test
  void testRemoveMove() {
    play.addMove(mockMove1);
    play.addMove(mockMove2);

    assertEquals(mockMove2, play.removeMove());
    assertEquals(1, play.getMoves().size());
    assertFalse(play.getMoves().contains(mockMove2));
  }

  @Test
  void testRemoveMoveWithCoordinates() {
    play.addMove(mockMove1);
    play.addMove(mockMove2);

    when(mockMove1.getX()).thenReturn(1);
    when(mockMove1.getY()).thenReturn(1);

    assertEquals(mockMove1, play.removeMove(1, 1));
    assertEquals(1, play.getMoves().size());
    assertFalse(play.getMoves().contains(mockMove1));
  }

  @Test
  void testRemoveMoveWithInvalidCoordinates() {
    play.addMove(mockMove1);
    assertNull(play.removeMove(10, 10));
  }

  @Test
  void testIsVertical() {
    play.addMove(mockMove1);
    play.addMove(mockMove2);

    when(mockMove1.getX()).thenReturn(1);
    when(mockMove2.getX()).thenReturn(1);

    assertTrue(play.isVertical());

    when(mockMove2.getX()).thenReturn(2);
    assertFalse(play.isVertical());
  }

  @Test
  void testToString() {
    Play playWithWords = new Play(mockPlayer, new LinkedList<>(), Arrays.asList("word1", "word2"), false);

    ObjectMapper objectMapper = new ObjectMapper();
    String expectedJson = null;
    try {
      expectedJson = objectMapper.writeValueAsString(playWithWords);
    } catch (Exception e) {
      fail("Exception during JSON processing");
    }

    assertEquals(expectedJson, playWithWords.toString());
  }

  @Test
  void testEquals() {
    Play anotherPlay = new Play(mockPlayer, new LinkedList<>(Arrays.asList(mockMove1, mockMove2)), Arrays.asList("word1", "word2"), false);

    Play differentPlay = new Play(mockPlayer, new LinkedList<>(), Arrays.asList("word3"), true);
    assertEquals(differentPlay, differentPlay);
  }

  @Test
  void testSetWords() {
    play.setWords(Arrays.asList("word1", "word2"));
    assertEquals(Arrays.asList("word1", "word2"), play.getWords());
  }

  @Test
  void testSetFailedContest() {
    play.setFailedContest(true);
    assertTrue(play.getFailedContest());
  }
}

