package interface_adapter.place_letter;

import use_case.confirm_play.ConfirmPlayOutputBoundary;
import use_case.place_letter.PlaceLetterOutputBoundary;
import use_case.place_letter.PlaceLetterOutputData;

public class ConfirmPlayPresenter implements ConfirmPlayOutputBoundary {
  private ConfirmPlayViewModel viewModel;

  public ConfirmPlayPresenter() {
    viewModel = new ConfirmPlayViewModel();
  }

  public ConfirmPlayViewModel getViewModel() {
    return viewModel;
  }

  @Override
  public void prepareSuccessView(ConfirmPlayOutputBoundary data) {
    viewModel.firePropertyChanged();
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'prepareSuccessView'");
  }

  @Override
  public void prepareFailView(String error) {
    viewModel.firePropertyChanged();
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'prepareFailView'");
  }
}
