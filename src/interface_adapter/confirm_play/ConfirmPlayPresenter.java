package interface_adapter.confirm_play;

import interface_adapter.GameViewModel;
import use_case.confirm_play.ConfirmPlayOutputBoundary;
import use_case.confirm_play.ConfirmPlayOutputData;

public class ConfirmPlayPresenter implements ConfirmPlayOutputBoundary {
    private GameViewModel viewModel;

    public ConfirmPlayPresenter(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public GameViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void prepareSuccessView(ConfirmPlayOutputData data) {
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.firePropertyChanged();
    }
}
