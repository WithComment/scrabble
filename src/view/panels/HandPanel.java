package view.panels;


import input_manager.InputManager;
import view.Input;
import view.buttons.HandButton;
import view.listeners.GridButtonListener;
import view.listeners.HandButtonListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import entity.Letter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class HandPanel extends JPanel {
    private ArrayList<HandButton> handButtons = new ArrayList<>();
    InputManager inputManager;
    public HandPanel(InputManager inputManager) {
        setLayout(new GridLayout(1, 7));
        this.inputManager = inputManager;
    }
    public void setHand(List<Letter> newHand) {
        removeAll();
        for(int i = 0; i < newHand.size(); i++) {
            HandButton button = new HandButton(i);
            button.setText(newHand.get(i).toString());
            button.setPreferredSize(new Dimension(100, 100));
            button.setBorder(new LineBorder(Color.black));
            button.addMouseListener(new HandButtonListener(i, inputManager));
            add(button);
            handButtons.add(button);
        }
    }

    public void setHandTile(String letter, int index){
        handButtons.get(index).setText(letter);
    }
}
