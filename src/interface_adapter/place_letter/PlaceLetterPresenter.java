package interface_adapter.place_letter;

import use_case.place_letter.PlaceLetterOutputBoundary;
import use_case.place_letter.PlaceLetterOutputData;

public class PlaceLetterPresenter implements PlaceLetterOutputBoundary {
  private PlaceLetterViewModel viewModel;

  public PlaceLetterPresenter() {
    viewModel = new PlaceLetterViewModel();
  }

  public PlaceLetterViewModel getViewModel() {
    return viewModel;
  }

  @Override
  public void prepareSuccessView(PlaceLetterOutputData data) {
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
