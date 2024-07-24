package use_case.redraw_letters;

public class RedrawOutputData {
    private boolean drawSuccessful;

    public RedrawOutputData(boolean drawSuccessful) {
        this.drawSuccessful = drawSuccessful;
    }

    public boolean isDrawSuccessful() {
        return drawSuccessful;
    }
}