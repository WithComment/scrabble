package view.buttons;

import javax.swing.*;

public class BoardButton extends JButton {
    private int[] coords;
    public BoardButton(int[] coords) {
        this.coords = coords;
    }
    public int[] getCoords() {
        return coords;
    }
}
