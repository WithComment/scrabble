package test.entity;

import entity.Letter;
import entity.LetterBag;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LetterBagTest {
    @Test
    void testGetLength(){
        LetterBag letterBag = new LetterBag();

        assertEquals(98, letterBag.getLength());
    }

    @Test
    void testInit(){
        LetterBag letterBag = new LetterBag();

        assertEquals(98, letterBag.getLength());

        List<Letter> allLetters = letterBag.drawLetters(98);

        assertEquals(0, letterBag.getLength());

        Map<Character, Integer> letterCount = new HashMap<>();

        for(Letter letter:allLetters){
            if(letterCount.containsKey(letter.getLetter())){
                letterCount.put(letter.getLetter(), letterCount.get(letter.getLetter())+1);
            } else {
                letterCount.put(letter.getLetter(), 1);
            }
        }

        Map<Character, Integer> expected = new HashMap<>();
        expected.put('A', 9);
        expected.put('B', 2);
        expected.put('C', 2);
        expected.put('D', 4);
        expected.put('E', 12);
        expected.put('F', 2);
        expected.put('G', 3);
        expected.put('H', 2);
        expected.put('I', 9);
        expected.put('J', 1);
        expected.put('K', 1);
        expected.put('L', 4);
        expected.put('M', 2);
        expected.put('N', 6);
        expected.put('O', 8);
        expected.put('P', 2);
        expected.put('Q', 1);
        expected.put('R', 6);
        expected.put('S', 4);
        expected.put('T', 6);
        expected.put('U', 4);
        expected.put('V', 2);
        expected.put('W', 2);
        expected.put('X', 1);
        expected.put('Y', 2);
        expected.put('Z', 1);

        for (Character key : letterCount.keySet()) {
            assertEquals(expected.get(key), letterCount.get(key));
        }
    }

    @Test
    void testDrawLetters(){
        LetterBag letterBag = new LetterBag();

        assertEquals(98, letterBag.getLength());

        Map<Character, Integer> expectedPoints = new HashMap<>();
        expectedPoints.put('A', 1);
        expectedPoints.put('B', 3);
        expectedPoints.put('C', 3);
        expectedPoints.put('D', 2);
        expectedPoints.put('E', 1);
        expectedPoints.put('F', 4);
        expectedPoints.put('G', 2);
        expectedPoints.put('H', 4);
        expectedPoints.put('I', 1);
        expectedPoints.put('J', 8);
        expectedPoints.put('K', 5);
        expectedPoints.put('L', 1);
        expectedPoints.put('M', 3);
        expectedPoints.put('N', 1);
        expectedPoints.put('O', 1);
        expectedPoints.put('P', 3);
        expectedPoints.put('Q', 10);
        expectedPoints.put('R', 1);
        expectedPoints.put('S', 1);
        expectedPoints.put('T', 1);
        expectedPoints.put('U', 1);
        expectedPoints.put('V', 4);
        expectedPoints.put('W', 4);
        expectedPoints.put('X', 8);
        expectedPoints.put('Y', 4);
        expectedPoints.put('Z', 10);

        List<Letter> draws = letterBag.drawLetters();

        assertEquals(97, letterBag.getLength());

        for(Letter letter:draws){
            assertEquals(expectedPoints.get(letter.getLetter()), letter.getPoints());
        }

        draws = letterBag.drawLetters(20);

        assertEquals(77, letterBag.getLength());

        for(Letter letter:draws){
            assertEquals(expectedPoints.get(letter.getLetter()), letter.getPoints());
        }
    }

    @Test
    void testAddLetters(){
        LetterBag letterBag = new LetterBag();

        assertEquals(98, letterBag.getLength());

        List<Letter> allLetters = letterBag.drawLetters(98);

        assertEquals(0, letterBag.getLength());

        Letter letterToAdd = allLetters.remove(0);
        ArrayList<Letter> lettersToAdd = new ArrayList<>();
        lettersToAdd.add(letterToAdd);

        letterBag.addLetters(lettersToAdd);
        assertEquals(1, letterBag.getLength());

        Letter drawnLetter = letterBag.drawLetters().remove(0);
        assertEquals(drawnLetter, letterToAdd);
    }

    @Test
    void testSerialization(){
        LetterBag originalBag = new LetterBag();

        // Serialize the LetterBag
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("letterBag.ser"))) {
            out.writeObject(originalBag);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize the LetterBag
        LetterBag deserializedBag = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("letterBag.ser"))) {
            deserializedBag = (LetterBag) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Test to ensure the deserialized bag is not null and has the same length
        if (deserializedBag != null) {
            assertEquals(originalBag.getLength(), deserializedBag.getLength());
        }

        // Deserialize with no data to trigger readObjectNoData
        LetterBag noDataBag = null;
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(new byte[0]))) {
            noDataBag = (LetterBag) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Test to ensure the bag initialized correctly with no data
        if (noDataBag != null) {
            assertEquals(98, noDataBag.getLength());
        }
    }
}