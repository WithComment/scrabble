package use_case.place_letter;


import entity.Board;
import entity.Letter;
import entity.Move;
import entity.Play;
import entity.Player;

public class PlaceLetterInteractor implements PlaceLetterInputBoundary {

  private final PlaceLetterOutputBoundary presenter;

  public PlaceLetterInteractor(
    PlaceLetterOutputBoundary presenter
  ) {
    this.presenter = presenter;
  }

  @Override
  public void execute(PlaceLetterInputData input) {
    int x = input.getX();
    int y = input.getY();
    Letter letter = input.getLetter();
    Play play = input.getPlay();
    Board board = input.getBoard();
    Player player = play.getPlayer();
    
    if (!board.getCell(x, y).isEmpty()) {
      presenter.prepareFailView("This grid is occupied!");
    } else if (!player.getInventory().contains(letter)) {
      presenter.prepareFailView("You don't have the letter in your inventory!");
    } else {
      play.addMove(new Move(x, y, letter));
      board.setCell(x, y, letter);
      player.removeLetter(letter);
      presenter.prepareSuccessView(new PlaceLetterOutputData(board, player.getInventory()));
    }
  }
}
