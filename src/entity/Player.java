package entity;

import java.util.ArrayList;

public class Player {
    private int id;
    private ArrayList<Letter> inventory;
    private int score;
    private Play currentPlay;

    public Player(int id) {
        this.id = id;
        this.inventory = new ArrayList<>();
        this.score = 0;
        this.currentPlay = new Play();
    }

    public void redrawTiles(ArrayList<Letter> tilesRedrawn) {
        this.inventory.clear();
        this.inventory.addAll(tilesRedrawn);
    }

    public void placeLetter(Letter letter, int x, int y) {
        //TODO Add logic to place a letter on the board at position (x, y)
        currentPlay.addLetter(letter, x, y);
        inventory.remove(letter);
    }

    public void removeLetter(int x, int y) {
        //TODO Add logic to remove a letter from the board at position (x, y)
        Letter removedLetter = currentPlay.removeLetter(x, y);
        if (removedLetter != null) {
            inventory.add(removedLetter);
        }
    }

    public int getScore(ArrayList<Letter> lettersPlayed) {
        // TODO Add logic to calculate the score based on letters played
        int calculatedScore = 0;
        for (Letter letter : lettersPlayed) {
            calculatedScore += letter.getPoints();
        }
        return calculatedScore;
    }

    public void endTurn() {
        //TODO Add logic to end the player's turn
        this.score += getScore(currentPlay.getLetters());
        currentPlay.clear();
    }

    public boolean contest() {
        if (){
            return true
        }
        return false;
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

