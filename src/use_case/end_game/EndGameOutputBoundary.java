package use_case.end_game;

public interface EndGameOutputBoundary {
    void prepareSuccessView(EndGameOutputData endGameOutputData);

    void prepareFailView(String error);
}
