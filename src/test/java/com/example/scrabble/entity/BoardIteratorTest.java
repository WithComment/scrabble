package com.example.scrabble.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardIteratorTest {

    private Board board;
    private BoardIterator iterator;

    @BeforeEach
    public void setUp() {
        board = new Board();
        iterator = new BoardIterator(board);
    }

    @Test
    public void testIteratorHasNext() {
        // Test that the iterator correctly identifies if there are more elements
        assertTrue(iterator.hasNext());
        for (int i = 0; i < 225; i++) {
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorNext() {
        // Test that the iterator correctly retrieves the next element
        Tile firstTile = iterator.next();
        assertNotNull(firstTile);

        Tile lastTile = null;
        for (int i = 0; i < 224; i++) {
            lastTile = iterator.next();
        }
        assertNotNull(lastTile);
    }

    @Test
    public void testIteratorThrowsExceptionWhenNoMoreElements() {
        // Test that the iterator throws a NoSuchElementException when there are no more elements
        for (int i = 0; i < 225; i++) {
            iterator.next();
        }
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
}
