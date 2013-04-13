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
        setLocationRelativeTo(null);
        setTitle("EZ Alch by caa4444");
        setLayout(grid);
        setSize(260, 240);
        final GridBagConstraints C = new GridBagConstraints();

        C.weightx = 1;
        C.weighty = 1;

        for (int i = 0; i < 28; i++) {
            boxes[i] = new JCheckBox("slot " + (i + 1), false);
            if (i < 4) {
                boxes[i].setSelected(true);
            }
            boxes[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
            C.gridx = i % 4;
            C.gridy = i / 4;
            pane.add(boxes[i], C);
        }
        C.gridx = 0;
        C.gridy = 7;
        pane.add(btnStart, C);
        label1.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
        C.gridwidth = GridBagConstraints.REMAINDER;
        C.gridx = 1;
        pane.add(label1, C);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startScript();
            }
        });
    }

    public void startScript() {
        for (int i = 0; i < 28; i++) {
            Variables.slotsToAlch[i] = boxes[i].isSelected();
        }
        dispose();
        Variables.guiIsDone = true;
        Variables.timer = new Timer(0);
    }
}
