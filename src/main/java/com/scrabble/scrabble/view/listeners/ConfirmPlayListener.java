package view.listeners;

import input_manager.InputManager;
import view.Input;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ConfirmPlayListener implements MouseListener{
    InputManager inputManager;

    public ConfirmPlayListener(InputManager inputManager){
        this.inputManager = inputManager;
    }

    public void mouseClicked(MouseEvent e) {
        Input input = new Input("ConfirmPlay");
        inputManager.handleInput(input);
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
