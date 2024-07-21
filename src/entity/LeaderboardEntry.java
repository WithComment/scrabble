package entity;

/**
 * Represents an entry in the leaderboard, holding a player and it's score.
 */
public class LeaderboardEntry implements Comparable<LeaderboardEntry>{
    private final Player player;
    private final int score;

    /**
     * Constructs a new LeaderboardEntry with the specified player and score.
     * @param player The player.
     * @param score The score of the player.
     */
    public LeaderboardEntry(Player player, int score) {
        this.player = player;
        this.score = score;
    }

    /**
     * Retrieves the player associated with this leaderboard entry.
     * @return The player.
     */
    public Player getPlayer() {
        return player;
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