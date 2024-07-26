package view.listeners;

import input_manager.InputManager;
import view.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HandButtonListener implements MouseListener {
    private int positionInHand;
    InputManager inputManager;

    public HandButtonListener(int positionInHand, InputManager inputManager){
        this.positionInHand = positionInHand;
        this.inputManager = inputManager;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) { // left click
            Input input = new Input(positionInHand, "lclick");
            inputManager.handleInput(input);        }
        if (e.getButton() == MouseEvent.BUTTON3) { //right click
            Input input = new Input(positionInHand, "rclick");
            inputManager.handleInput(input);             }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
