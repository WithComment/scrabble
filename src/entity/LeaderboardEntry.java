package entity;

public class LeaderboardEntry implements Comparable<LeaderboardEntry>{
    private final int playerId;
    private final int score;

    public LeaderboardEntry(int playerId, int score) {
        this.playerId = playerId;
        this.score = score;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(LeaderboardEntry o)
    {
        return this.score - o.getScore();
    }
}
