package entity;

import java.util.LinkedList;
import java.util.List;

public class Play {
  private final Player player;
  private final List<int[]> positions;
  private final List<Letter> letters;
  private int score;

  public Play(Player player) {
    this.player = player;
    this.positions = new LinkedList<int[]>();
    this.letters = new LinkedList<Letter>();
    this.score = 0;
  }

  public void addLetter(
    int[] position,
    Letter letter,
    int score
  ) {
    this.positions.add(position);
    this.letters.add(letter);
    this.score += score;
  }

  public List<int[]> getPositions() {
    return positions;
  }

  public List<Letter> getLetters() {
    return letters;
  }

  public int getScore() {
    return score;
  }

  public Player getPlayer() {
    return player;
  }
}
