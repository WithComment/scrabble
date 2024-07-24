package use_case.redraw_letters;

public interface RedrawOutputBoundary {
    void prepareSuccessView(RedrawOutputData redrawOutputData);

    void prepareFailedView(String error);
}
