package com.example.scrabble.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LetterBagTest {

    private LetterBag letterBag;

    @BeforeEach
    public void setUp() {
        letterBag = new LetterBag();
    }

    @Test
    public void testInitializeBag() {
        LetterBag customLetterBag = new LetterBag();
        assertEquals(100, customLetterBag.getLength());  // Assuming the default bag has 100 letters
    }

    @Test
    public void testDrawLetter() {
        List<Letter> drawnLetters = letterBag.drawLetters(1);
        assertEquals(1, drawnLetters.size());
        assertEquals(99, letterBag.getLength());
    }

    @Test
    public void testDrawMultipleLetters() {
        int numToDraw = 5;
        List<Letter> drawnLetters = letterBag.drawLetters(numToDraw);
        assertEquals(numToDraw, drawnLetters.size());
        assertEquals(100 - numToDraw, letterBag.getLength());
    }

    @Test
    public void testAddLetters() {
        List<Letter> lettersToAdd = Arrays.asList(new Letter('A', 1), new Letter('B', 3));
        letterBag.addLetters(lettersToAdd);
        assertEquals(102, letterBag.getLength());
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(letterBag);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        LetterBag deserializedLetterBag = (LetterBag) in.readObject();

        assertEquals(letterBag.getBag().size(), deserializedLetterBag.getBag().size());
    }

    @Test
    public void testEquals() {
        List<Letter> letters = Arrays.asList(new Letter('A', 1), new Letter('B', 3));
        LetterBag bag1 = new LetterBag(letters);
        LetterBag bag2 = new LetterBag(letters);

        assertEquals(bag1, bag2);

        LetterBag bag3 = new LetterBag();
        assertNotEquals(bag1, bag3);
    }

    @Test
    public void testToString() {
        String expectedOutput = "LetterBag contains:\n" + "A\nB\n";  // Depending on the contents of the default bag
        List<Letter> letters = Arrays.asList(new Letter('A', 1), new Letter('B', 3));
        LetterBag customBag = new LetterBag(letters);

        assertEquals(expectedOutput, customBag.toString());
    }

    @Test
    public void testDrawLetterFromEmptyBag() {
        LetterBag emptyBag = new LetterBag(new ArrayList<>());
        List<Letter> drawnLetters = emptyBag.drawLetters(1);
        assertEquals(0, drawnLetters.size());
    }
}

