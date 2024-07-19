package view.buttons;

import javax.swing.*;

public class HandButton extends JButton {
    private int positionInHand;
    public HandButton(int positionInHand) {
        this.positionInHand = positionInHand;
    }
    public int getPositionInHand() {
        return positionInHand;
    }
}
