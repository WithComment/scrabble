package use_case.place_letter;

import entity.Board;
import entity.Letter;
import entity.Move;
import entity.Play;

public class PlaceLetterInteractor implements PlaceLetterInputBoundary {

  private final PlaceLetterOutputBoundary presenter;

  public PlaceLetterInteractor(PlaceLetterOutputBoundary presenter) {
    this.presenter = presenter;
  }

  @Override
  public void execute(PlaceLetterInputData input) {
    int x = input.getX();
    int y = input.getY();
    Board board = input.getBoard();
    Play play = input.getPlay();
    Letter letter = input.getLetter();
    
    if (!board.getCell(x, y).isEmpty()) {
      presenter.prepareFailView("This grid is occupied!");
    } else {
      play.addMove(new Move(x, y, letter));
      board.getCell(x, y).setLetter(letter);
      presenter.prepareSuccessView(new PlaceLetterOutputData(board));
    }
  }
}
