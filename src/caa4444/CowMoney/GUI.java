package caa4444.cowmoney;

import caa4444.cowmoney.misc.Variables;
import org.powerbot.game.api.methods.input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JCheckBox killCows = new JCheckBox("Kill Cows?", true);
    private final JList<Mouse.Speed> options = new JList<Mouse.Speed>(Mouse.Speed.values());

    public GUI() {
        setLocationRelativeTo(null);
        setTitle("CowMoney by caa4444");
        setLayout(new GridBagLayout());
        setSize(200, 210);

        final Container pane = getContentPane();

        options.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        options.setLayoutOrientation(JList.VERTICAL);
        options.setSelectedIndex(4);

        final JLabel label1 = new JLabel(
                "Select a Mouse Speed!");
        label1.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));

        final GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        pane.add(label1, c);

        c.gridy = 1;
        c.gridheight = 4;
        pane.add(options, c);

        c.gridy = 5;
        c.gridheight = 1;
        pane.add(killCows, c);

        c.gridy = 6;
        final JButton btnStart = new JButton("Start");
        pane.add(btnStart, c);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startScript();
            }
        });
    }

    void startScript() {
        Variables.speed = Mouse.Speed.values()[options.getSelectedIndex()];
        Variables.killCows = killCows.isSelected();
        dispose();
        Variables.guiIsDone = true;
    }
}