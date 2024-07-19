package interface_adapter.confirm_play;

import interface_adapter.place_letter.ConfirmPlayViewModel;
import use_case.confirm_play.ConfirmPlayOutputBoundary;
import use_case.confirm_play.ConfirmPlayOutputData;

public class ConfirmPlayPresenter implements ConfirmPlayOutputBoundary {
    private ConfirmPlayViewModel viewModel;

    public ConfirmPlayPresenter() {
        viewModel = new ConfirmPlayViewModel();
    }

    public ConfirmPlayViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void prepareSuccessView(ConfirmPlayOutputData data) {
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
