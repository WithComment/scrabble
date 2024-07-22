package interface_adapter.confirm_play;

import entity.Board;
import entity.Play;
import use_case.confirm_play.ConfirmPlayInputBoundary;
import use_case.confirm_play.ConfirmPlayInputData;

public class ConfirmPlayController {  
  private ConfirmPlayInputBoundary interactor;

  public ConfirmPlayController(ConfirmPlayInputBoundary interactor) {
    this.interactor = interactor;
  }

  public void execute(Play play, Board board) {
    ConfirmPlayInputData data = new ConfirmPlayInputData(play, board);
    interactor.execute(data);
  }
}
