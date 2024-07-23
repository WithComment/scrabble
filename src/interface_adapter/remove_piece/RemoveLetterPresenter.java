package interface_adapter.remove_piece;

import interface_adapter.GameViewModel;
import use_case.remove_letter.RemoveLetterOutputBoundary;
import use_case.remove_letter.RemoveLetterOutputData;

public class RemoveLetterPresenter implements RemoveLetterOutputBoundary {
    private GameViewModel viewModel;
    public RemoveLetterPresenter(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void prepareFailureView(RemoveLetterOutputData removeLetterOutputData) {
    }
    public void prepareSuccessView(RemoveLetterOutputData removeLetterOutputData) {
        viewModel.setBoard(removeLetterOutputData.getBoard());
        viewModel.setHand(removeLetterOutputData.getHandCharacters());
        viewModel.firePropertyChanged();
    }
}
