package view;

import input_manager.InputManager;

public class Input {
    private int x;
    private int y;
    private String input;
    private int positionInHand;

    public Input(int x, int y, String input) {
        this.x = x;
        this.y = y;
        this.input = input;
    }
    public Input(int positionInHand, String input) {
        this.positionInHand = positionInHand;
        this.input = input;
    }
}
