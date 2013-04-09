package pro.geektalk.alcher;

import org.powerbot.game.api.util.Timer;
import pro.geektalk.alcher.misc.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;

    GridBagLayout grid;
    Container pane;

    JLabel label1 = new JLabel(
            "Bind high alch to the '0' key!");
    JCheckBox[] boxes = new JCheckBox[28];
    JButton btnStart = new JButton("Start");

    public GUI() {
        pane = getContentPane();
        grid = new GridBagLayout();
        this.setLocationRelativeTo(null);
        this.setTitle("EZ Alch by caa4444");
        this.setLayout(grid);
        this.setSize(260, 240);
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;

        for (int i = 0; i < 28; i++) {
            boxes[i] = new JCheckBox("slot " + (i + 1), false);
            if (i < 4) boxes[i].setSelected(true);
            boxes[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
            c.gridx = i % 4;
            c.gridy = i / 4;
            pane.add(boxes[i], c);
        }
        c.gridx = 0;
        c.gridy = 7;
        pane.add(btnStart, c);
        label1.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 1;
        pane.add(label1, c);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startScript(e);
            }
        });
    }

    public void startScript(ActionEvent e) {
        for (int i = 0; i < 28; i++) {
            Variables.slotsToAlch[i] = boxes[i].isSelected();
        }
        this.dispose();
        Variables.guiIsDone = true;
        Variables.timer = new Timer(0);
    }
}
