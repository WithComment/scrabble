package interface_adapter.end_game;

import interface_adapter.GameOverViewModel;
import interface_adapter.GameViewModel;
import use_case.end_game.EndGameOutputBoundary;
import use_case.end_game.EndGameOutputData;

public class EndGamePresenter implements EndGameOutputBoundary {
    private GameOverViewModel gameOverModel;
    private GameViewModel gameModel;

    public EndGamePresenter(GameOverViewModel gameOverModel, GameViewModel gameModel){
        this.gameOverModel = gameOverModel;
        this.gameModel = gameModel;
    }

    @Override
    public void prepareSuccessView(EndGameOutputData endGameOutputData){
        gameOverModel.setWinners(endGameOutputData.getWinners());
        gameOverModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error){
        gameModel.setErrorMessage(error);
        gameModel.firePropertyChanged();
    };

}
