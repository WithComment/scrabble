package view.panels;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class HandPanel extends JPanel {
    private ArrayList<JLabel> handLabels = new ArrayList<>();
    public HandPanel() {
        setLayout(new GridLayout(1, 7));
    }

    public void setHand(ArrayList<String> hand) {
        removeAll();
        for(int i = 0; i < hand.size(); i++) {
            JLabel label = new JLabel(hand.get(i));
            label.setBorder(new LineBorder(Color.black));
            add(label);
            handLabels.add(label);
        }
    }

    public void setHandTile(String letter, int index){
        handLabels.get(index).setText(letter);
    }
}
