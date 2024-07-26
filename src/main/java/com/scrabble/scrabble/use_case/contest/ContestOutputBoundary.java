package use_case.contest;

public interface ContestOutputBoundary {
    void prepareSuccessView(ContestOutputData contestOutputData);

    void prepareFailedView(String error);
}
