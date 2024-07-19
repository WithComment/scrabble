package entity;

/**
 * Represents an entry in the leaderboard, holding a player's ID and score.
 */
public class LeaderboardEntry implements Comparable<LeaderboardEntry>{
    private final int playerId;
    private final int score;

    /**
     * Constructs a new LeaderboardEntry with the specified player ID and score.
     * @param playerId The unique ID of the player.
     * @param score The score of the player.
     */
    public LeaderboardEntry(int playerId, int score) {
        this.playerId = playerId;
        this.score = score;
    }

    /**
     * Retrieves the player ID associated with this leaderboard entry.
     * @return The player's ID.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Retrieves the score associated with this leaderboard entry.
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Compares this LeaderboardEntry with another based on their scores.
     * @param o The other LeaderboardEntry to compare to.
     * @return A negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    public int compareTo(LeaderboardEntry o)
    {
        return this.score - o.getScore();
    }
}