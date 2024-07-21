package interface_adapter.get_winner;

import interface_adapter.GameOverViewModel;
import use_case.get_winner.*;

public class GetWinnerPresenter implements GetWinnerOutputBoundary {

    private GameOverViewModel viewModel;

    public GetWinnerPresenter(GameOverViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(GetWinnerOutputData outputData) {
        viewModel.setWinners(outputData.getWinner());
        viewModel.firePropertyChanged();
    }
}
