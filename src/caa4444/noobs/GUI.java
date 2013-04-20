package caa4444.noobs;

import caa4444.noobs.misc.Variables;
import org.powerbot.game.api.util.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Container pane;

    private final JLabel label1 = new JLabel(
            "Enter the names of the NPCs you want to kill separated by commas!");
    private final JButton btnStart = new JButton("Start");
    private final JTextField idBox = new JTextField();
    private final GridBagConstraints c = new GridBagConstraints();

    public GUI() {
        setLocationRelativeTo(null);
        setTitle("Slaughterhouse by caa4444");
        setLayout(new GridBagLayout());
        setSize(300, 100);

        pane = getContentPane();


        label1.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        pane.add(label1, c);

        c.gridy = 1;
        pane.add(idBox, c);

        c.gridy = 2;
        c.gridheight = 1;
        pane.add(btnStart, c);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startScript();
            }
        });
    }

    void startScript() {
        Variables.npcs = idBox.getText().split(",");
        for (int i = 0; i < Variables.npcs.length; i++) {
            Variables.npcs[i] = Variables.npcs[i].trim();
        }
        dispose();
        Variables.guiIsDone = true;
        Variables.timer = new Timer(1000);
    }
}