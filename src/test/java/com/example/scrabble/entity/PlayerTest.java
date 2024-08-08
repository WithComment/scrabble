package com.example.scrabble.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

class PlayerTest {

    private Player player;
    private Letter mockLetter1;
    private Letter mockLetter2;

    @BeforeEach
    void setUp() {
        mockLetter1 = mock(Letter.class);
        mockLetter2 = mock(Letter.class);

        when(mockLetter1.getLetter()).thenReturn('A');
        when(mockLetter2.getLetter()).thenReturn('B');

        player = new Player(1); // Test default constructor
    }

    @Test
    void testConstructorWithParams() {
        Player playerWithParams = new Player("John Doe", 2);

        assertEquals("John Doe", playerWithParams.getName());
        assertEquals(2, playerWithParams.getId());
        assertEquals(0, playerWithParams.getScore());
        assertEquals(0, playerWithParams.getTempScore());
        assertTrue(playerWithParams.getInventory().isEmpty());
    }

    @Test
    void testConstructorWithJson() {
        JSONObject json = new JSONObject()
                .put("id", 3)
                .put("name", "Jane Doe")
                .put("inventory", new LinkedList<>()) // Empty inventory for simplicity
                .put("score", 10)
                .put("tempScore", 5);

        Player playerFromJson = new Player(json);

        assertEquals("Jane Doe", playerFromJson.getName());
        assertEquals(3, playerFromJson.getId());
        assertEquals(0, playerFromJson.getScore());
        assertEquals(0, playerFromJson.getTempScore());
        assertTrue(playerFromJson.getInventory().isEmpty());
    }

    @Test
    void testAddLetter() {
        player.addLetter(mockLetter1);
        assertTrue(player.getInventory().contains(mockLetter1));
    }

    @Test
    void testRemoveLetter() {
        player.addLetter(mockLetter1);
        player.removeLetter(mockLetter1);
        assertFalse(player.getInventory().contains(mockLetter1));
    }

    @Test
    void testRemoveLetterByChar() {
        player.addLetter(mockLetter1);
        when(mockLetter1.getLetter()).thenReturn('A');

        assertEquals(mockLetter1, player.removeLetter('A'));
        assertFalse(player.getInventory().contains(mockLetter1));
    }

    @Test
    void testAddScore() {
        player.addScore(10);
        assertEquals(10, player.getScore());
    }

    @Test
    void testSetTempScore() {
        player.setTempScore(20);
        assertEquals(20, player.getTempScore());
    }

    @Test
    void testConfirmTempScore() {
        player.setTempScore(15);
        player.confirmTempScore();
        assertEquals(15, player.getScore());
        assertEquals(0, player.getTempScore());
    }

    @Test
    void testResetTempScore() {
        player.setTempScore(25);
        player.resetTempScore();
        assertEquals(0, player.getTempScore());
    }

    @Test
    void testEquals() {
        Player anotherPlayer = new Player(1);
        anotherPlayer.addLetter(mockLetter1);
        assertNotEquals(player, anotherPlayer);

        Player differentPlayer = new Player(2);
        assertNotEquals(player, differentPlayer);
    }

    @Test
    void testCompareTo() {
        Player higherScorePlayer = new Player(2);
        higherScorePlayer.addScore(10);

        assertTrue(higherScorePlayer.compareTo(player) > 0);
        assertTrue(player.compareTo(higherScorePlayer) < 0);
        assertEquals(0, player.compareTo(new Player(1)));
    }

//    @Test
//    void testSerialization() {
//        player.addLetter(mockLetter1);
//        player.addScore(50);
//
//        try {
//            // Serialize
//            ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
//            oos.writeObject(player);
//
//            // Deserialize
//            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(((ByteArrayOutputStream) oos.getOutputStream()).toByteArray()));
//            Player deserializedPlayer = (Player) ois.readObject();
//
//            assertEquals(player, deserializedPlayer);
//        } catch (IOException | ClassNotFoundException e) {
//            fail("Exception during serialization/deserialization");
//        }
//    }
}

