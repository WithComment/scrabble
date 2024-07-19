package view.panels;


import view.buttons.HandButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class HandPanel extends JPanel {
    private ArrayList<HandButton> handButtons = new ArrayList<>();

    public HandPanel() {
        setLayout(new GridLayout(1, 7));
    }
    public void setHand(ArrayList<String> hand) {
        removeAll();
        for(int i = 0; i < hand.size(); i++) {
            HandButton button = new HandButton(i);
            button.setText(hand.get(i));
            button.setPreferredSize(new Dimension(100, 100));
            button.setBorder(new LineBorder(Color.black));
            button.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println(((HandButton) e.getSource()).getPositionInHand()); //TODO: add onClick function that alerts InputManager when hand elt pressed
                        }
                    }
            );
            add(button);
            handButtons.add(button);
        }
    }

    public void setHandTile(String letter, int index){
        handButtons.get(index).setText(letter);
    }
}
