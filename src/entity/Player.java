package entity;

import java.util.ArrayList;

public class Player {
    private final int id;
    private ArrayList<Letter> inventory;
    private int score;
    private int unstableScore;
    public Player(int id) {
        this.id = id;
        this.inventory = new ArrayList<>();
        this.score = 0;
        this.unstableScore = 0;
    }

    public void removeLetter(Iterable<Letter> tiles) {
        for (Letter letter : tiles) {
            this.inventory.remove(letter);
        }
    }

    public void removeLetter(Letter letter) {
        this.inventory.remove(letter);
    }

    public void addLetter(ArrayList<Letter> tiles) {

        this.inventory.addAll(tiles);
    }


    public void updateScore(ArrayList<Integer> ScoresOfWords) {

        //Add scores to player
        for (Integer score : ScoresOfWords) {
            this.score += score;
            unstableScore += score;
        }
    }

    public void NotContested(){
        this.unstableScore = 0;
    }

    public void BeContested() {
        this.score = this.score - this.unstableScore;
        this.unstableScore = 0;
    }


    public int getId() {
        return id;
    }

    public ArrayList<Letter> getInventory() {
        return inventory;
    }

    public int getScore() {
        return score;
    }


}

