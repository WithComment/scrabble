package com.example.scrabble.entity;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class LetterTest {

  @Test
  public void testConstructorAndGetters() {
    Letter letter = new Letter('A', 1);
    assertEquals('A', letter.getLetter());
    assertEquals(1, letter.getPoints());
  }

  @Test
  public void testJsonConstructor() {
    JSONObject json = new JSONObject();
    json.put("letter", "B");
    json.put("points", 3);
    Letter letter = new Letter(json);
    assertEquals('B', letter.getLetter());
    assertEquals(3, letter.getPoints());
  }

  @Test
  public void testEquality() {
    Letter letter1 = new Letter('C', 3);
    Letter letter2 = new Letter('C', 3);
    Letter letter3 = new Letter('D', 2);
    assertEquals(letter1, letter2);
    assertNotEquals(letter1, letter3);
  }

  @Test
  public void testToString() {
    Letter letter = new Letter('E', 1);
    assertEquals("E", letter.toString());
  }

  @Test
  public void testClone() {
    Letter original = new Letter('F', 4);
    Letter clone = original.clone();
    assertEquals(original, clone);
    assertNotSame(original, clone);
  }

//  @Test
//  public void testSerialization() throws IOException, ClassNotFoundException {
//    Letter letter = new Letter('G', 2);
//    ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
//    ObjectOutputStream objectOutStream = new ObjectOutputStream(byteOutStream);
//    objectOutStream.writeObject(letter);
//
//    ByteArrayInputStream byteInStream = new ByteArrayInputStream(byteOutStream.toByteArray());
//    ObjectInputStream objectInStream = new ObjectInputStream(byteInStream);
//    Letter deserializedLetter = (Letter) objectInStream.readObject();
//
//    assertEquals(letter, deserializedLetter);
//    assertEquals('G', deserializedLetter.getLetter());
//    assertEquals(2, deserializedLetter.getPoints());
//  }

  @Test
  public void testInvalidJsonConstructor() {
    JSONObject json = new JSONObject();
    json.put("letter", "H");
    // Points are missing in the JSON
    assertThrows(Exception.class, () -> {
      new Letter(json);
    });
  }
}

