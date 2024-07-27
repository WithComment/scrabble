package view.listeners;

import input_manager.InputManager;
import view.Input;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridButtonListener implements MouseListener{
    private int x;
    private int y;
    InputManager inputManager;

    public GridButtonListener(int x, int y, InputManager inputManager){
        this.x = x;
        this.y = y;
        this.inputManager = inputManager;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) { // left click
            Input input = new Input(x, y, "lclick");
            inputManager.handleInput(input);
        }
        if (e.getButton() == MouseEvent.BUTTON3) { //right click
            Input input = new Input(x, y, "rclick");
            inputManager.handleInput(input);
        }
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
