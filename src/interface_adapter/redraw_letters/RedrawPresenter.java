package interface_adapter.redraw_letters;

import interface_adapter.GameViewModel;
import use_case.redraw_letters.RedrawOutputBoundary;
import use_case.redraw_letters.RedrawOutputData;

public class RedrawPresenter implements RedrawOutputBoundary {
    private GameViewModel viewModel;

    public RedrawPresenter(GameViewModel viewModel){
        this.viewModel = viewModel;
    }

    public GameViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void prepareSuccessView(RedrawOutputData redrawOutputData) {
        // TODO
    }

    @Override
    public void prepareFailView(String error) {
        // TODO
    }
}
