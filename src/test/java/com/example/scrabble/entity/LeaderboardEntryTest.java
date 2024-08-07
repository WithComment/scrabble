package com.example.scrabble.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardEntryTest {

    @Test
    public void testConstructorAndGetters() {
        Player player = new Player(0);
        int score = 150;
        LeaderboardEntry entry = new LeaderboardEntry(player, score);

        assertEquals(player, entry.getPlayer());
        assertEquals(score, entry.getScore());
    }

    @Test
    public void testCompareTo() {
        Player player1 = new Player(0);
        Player player2 = new Player(1);

        LeaderboardEntry entry1 = new LeaderboardEntry(player1, 200);
        LeaderboardEntry entry2 = new LeaderboardEntry(player2, 150);

        // entry1 should be greater than entry2
        assertTrue(entry1.compareTo(entry2) > 0);

        // entry2 should be less than entry1
        assertTrue(entry2.compareTo(entry1) < 0);

        // Comparing an entry with itself should result in equality
        assertEquals(0, entry1.compareTo(entry1));
    }

    @Test
    public void testEquals() {
        Player player1 = new Player(0);
        Player player2 = new Player(1);

        LeaderboardEntry entry1 = new LeaderboardEntry(player1, 250);
        LeaderboardEntry entry2 = new LeaderboardEntry(player1, 250);
        LeaderboardEntry entry3 = new LeaderboardEntry(player2, 250);
        LeaderboardEntry entry4 = new LeaderboardEntry(player1, 300);

        // Same player and score should be equal
        assertEquals(entry1, entry2);

        // Different players should not be equal
        assertNotEquals(entry1, entry3);

        // Same player but different scores should not be equal
        assertNotEquals(entry1, entry4);
    }

    @Test
    public void testNotEqualsDifferentType() {
        Player player = new Player(0);
        LeaderboardEntry entry = new LeaderboardEntry(player, 300);

        // Should not be equal to a different type
        assertNotEquals(entry, new Object());
    }

    @Test
    public void testNotEqualsNull() {
        Player player = new Player(1);
        LeaderboardEntry entry = new LeaderboardEntry(player, 300);

        // Should not be equal to null
        assertNotEquals(entry, null);
    }
}

