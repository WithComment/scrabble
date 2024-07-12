package use_case.place_letter;

import java.util.ArrayList;

import entity.Board;
import entity.Letter;
import entity.Move;
import entity.Play;
import entity.Player;

public class PlaceLetterInteractor implements PlaceLetterInputBoundary {

  private final PlaceLetterOutputBoundary presenter;
  private final Board board;
  private final Play play;
  private Player player;

  public PlaceLetterInteractor(
    PlaceLetterOutputBoundary presenter, 
    Board board, 
    Play play
  ) {
    this.presenter = presenter;
    this.board = board;
    this.play = play;
    this.player = play.getPlayer();
  }

  @Override
  public void execute(PlaceLetterInputData input) {
    int x = input.getX();
    int y = input.getY();
    Letter letter = input.getLetter();
    
    if (!board.getCell(x, y).isEmpty()) {
      presenter.prepareFailView("This grid is occupied!");
    } else if (!player.getInventory().contains(letter)) {
      presenter.prepareFailView("You don't have the letter in your inventory!");
    } else {
      play.addMove(new Move(x, y, letter));
      board.getCell(x, y).setLetter(letter);
      player.removeLetter(letter);
      presenter.prepareSuccessView(new PlaceLetterOutputData(board));
    }
  }
}
