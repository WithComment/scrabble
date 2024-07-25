package interface_adapter.get_winner;

import entity.LeaderboardEntry;
import interface_adapter.GameOverViewModel;
import use_case.get_winner.*;

import java.util.LinkedList;
import java.util.List;

public class GetWinnerPresenter implements GetWinnerOutputBoundary {

    private GameOverViewModel viewModel;

    public GetWinnerPresenter(GameOverViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(GetWinnerOutputData outputData) {
        List<Integer> winners = new LinkedList<>();
        for (LeaderboardEntry entry : outputData.getWinner()) {
            winners.add(entry.getPlayer().getId());
        }
        viewModel.setWinners(winners);
        viewModel.firePropertyChanged();
    }
}
