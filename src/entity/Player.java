package entity;

import java.util.ArrayList;

public class Player {
    private int id;
    private ArrayList<Letter> inventory;
    private int score;
    private Play currentPlay;
    private int UnstableScore

    public Player(int id) {
        this.id = id;
        this.inventory = new ArrayList<>();
        this.score = 0;
        this.currentPlay = new Play();
        this.UnstableScore = 0;
    }

    public void removeLetter(ArrayList<Letter> tiles) {
        for (Letter letter : tiles) {
            this.inventory.remove(letter);
        }
    }

    public void addLetter(ArrayList<Letter> tiles) {
        for (Letter letter : tiles) {
            this.inventory.add(letter);
        }
    }


//    public void placeLetter(Letter letter, int x, int y) {
//        //TODO Add logic to place a letter on the board at position (x, y)
//        currentPlay.addLetter(letter, x, y);
//        inventory.remove(letter);
//    }
//
//    public void removeLetter(int x, int y) {
//        //TODO Add logic to remove a letter from the board at position (x, y)
//        Letter removedLetter = currentPlay.removeLetter(x, y);
//        if (removedLetter != null) {
//            inventory.add(removedLetter);
//        }
//    }

    public void UpdateScore(ArrayList<Integer> ScoresOfWords) {

        //Add scores to player
        for (Integer score : ScoresOfWords) {
            this.score += score;
            UnstableScore += score;
        }
    }


    public void BeContested() {
        this.score = this.score - this.UnstableScore;
        this.UnstableScore = 0;
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

    public Play getCurrentPlay() {
        return currentPlay;
    }
}

