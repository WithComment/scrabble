package view;

import input_manager.InputManager;

public class Input {
    private int x;
    private int y;
    private String input;
    private int positionInHand;
    private String type;

    public Input(int x, int y, String input) {
        this.x = x;
        this.y = y;
        this.input = input;
        this.type = "GridInput";
    }
    public Input(int positionInHand, String input) {
        this.positionInHand = positionInHand;
        this.input = input;
        this.type = "HandInput";
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public String getInput() {return input;}
    public int getPositionInHand() {return positionInHand;}
    public String getType() {return type;}
}
