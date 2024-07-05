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

    private void removeLetter(ArrayList<Letter> tiles) {
        for (Letter letter : tiles) {
            this.inventory.remove(letter);
        }
    }

    private void addLetter(ArrayList<Letter> tiles) {
        for (Letter letter : tiles) {
            this.inventory.add(letter);
        }
    }

    public void redrawTiles(int num, ArrayList<Letter> tiles) {
        this.inventory.clear();
        this.inventory.addAll(tilesRedrawn);
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

    public void UpdateScore(ArrayList<Letter> lettersPlayed) {
        // TODO Add logic to calculate the score based on letters played

        for (Letter letter : lettersPlayed) {
            calculatedScore += letter.getPoints();
        }
        return;
    }


    public boolean contest() {
        boolean isValid = validatePlay(currentPlay);
        System.out.println("Player " + id + " contest result: " + (isValid ? "Valid" : "Invalid"));
        return isValid;
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

