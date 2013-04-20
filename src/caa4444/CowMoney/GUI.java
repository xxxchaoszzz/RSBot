package caa4444.cowmoney;

import caa4444.cowmoney.misc.Loots;
import caa4444.cowmoney.misc.Variables;
import org.powerbot.game.api.methods.input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;


    private final JList<Mouse.Speed> mouseOpts = new JList<Mouse.Speed>(Mouse.Speed.values());
    private final JCheckBox killCows = new JCheckBox("Kill Cows?", true);
    private final JList<Loots> lootOpts = new JList<Loots>(Loots.values());

    public GUI() {
        setLocationRelativeTo(null);
        setTitle("CowMoney by caa4444");
        setLayout(new GridBagLayout());
        setSize(300, 210);

        final Container pane = getContentPane();

        final GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;

        final JLabel mouseLabel = new JLabel(
                "Select a Mouse Speed!");
        mouseLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
        pane.add(mouseLabel, c);

        c.gridy = 1;
        c.gridheight = 4;

        mouseOpts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mouseOpts.setLayoutOrientation(JList.VERTICAL);
        mouseOpts.setSelectedIndex(4);
        pane.add(mouseOpts, c);

        c.gridy = 5;
        c.gridheight = 1;

        pane.add(killCows, c);

        c.gridy = 6;

        final JButton btnStart = new JButton("Start");
        pane.add(btnStart, c);

        c.gridy = 0;
        c.gridx = 1;

        final JLabel lootLabel = new JLabel(
                "Select loot items!");
        lootLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
        pane.add(lootLabel, c);

        c.gridy = 1;
        c.gridheight = 4;

        lootOpts.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lootOpts.setLayoutOrientation(JList.VERTICAL);
        lootOpts.setSelectedIndex(0);
        pane.add(lootOpts, c);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startScript();
            }
        });
    }

    void startScript() {
        Variables.speed = mouseOpts.getSelectedValue();
        Variables.killCows = killCows.isSelected();
        Variables.loots = lootOpts.getSelectedValuesList().toArray(new Loots[1]);
        dispose();
        Variables.guiIsDone = true;
    }
}