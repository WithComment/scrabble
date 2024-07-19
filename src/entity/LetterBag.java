package entity;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LetterBag {
  private ArrayList<Letter> bag;

  public LetterBag() {
    bag = new ArrayList<>();
    initializeBag("static/letters.txt");
  }

  private void initializeBag(String filename){
    try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        char letter = parts[0].charAt(0);
        int points = Integer.parseInt(parts[1]);
        int count = Integer.parseInt(parts[2]);
        addLetters(letter, points, count);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addLetters(char letter, int points, int count) {
    for (int i = 0; i < count; i++) {
      bag.add(new Letter(letter, points));
    }
  }

  private Letter drawLetter(){
    if(bag.isEmpty()){
      return null;
    }
    else{
      Collections.shuffle(bag);
      return bag.remove(0);

    }
  }

  public void addLetters(ArrayList<Letter> letters){
    for(Letter letter : letters){
      bag.add(letter);
    }
  }

  //Overloaded method to draw a single letter
  public ArrayList<Letter> drawLetters() {
    ArrayList<Letter> draws = new ArrayList<>();
    draws.add(drawLetter());
    return draws;
  }

  //Overloaded method to draw multiple letters
  public ArrayList<Letter> drawLetters(int num) {
    ArrayList<Letter> draws = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      draws.add(drawLetter());
    }
    return draws;
  }

  public int getLength(){
    return bag.size();
  }
}


