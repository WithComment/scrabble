package interface_adapter.place_letter;

import interface_adapter.GameViewModel;
import use_case.place_letter.PlaceLetterOutputBoundary;
import use_case.place_letter.PlaceLetterOutputData;

public class PlaceLetterPresenter implements PlaceLetterOutputBoundary {
  private GameViewModel viewModel;

  public PlaceLetterPresenter(GameViewModel viewModel) {
    this.viewModel = viewModel;
  }

  public GameViewModel getViewModel() {
    return viewModel;
  }

  @Override
  public void prepareSuccessView(PlaceLetterOutputData data) {
    viewModel.firePropertyChanged();
    System.out.println(data.getBoard().getBoard().toString());
    System.out.println("Letter placed successfully");
  }

  @Override
  public void prepareFailView(String error) {
    viewModel.firePropertyChanged();
    System.out.println(error);
  }
}
