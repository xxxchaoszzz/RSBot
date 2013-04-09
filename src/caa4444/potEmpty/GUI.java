package caa4444.potEmpty;

import caa4444.potEmpty.misc.Methods;
import caa4444.potEmpty.misc.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;

    Container pane;

    JLabel label1 = new JLabel(
            "Enter the ID of the item to empty!");
    JButton btnStart = new JButton("Start");
    JTextField idBox = new JTextField();
    GridBagConstraints c = new GridBagConstraints();

    public GUI() {
        this.setLocationRelativeTo(null);
        this.setTitle("Potion Emptier by caa4444");
        this.setLayout(new GridBagLayout());
        this.setSize(200, 200);

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

    public void startScript() {
        try {
            if (idBox.getText().length() > 0)
                Variables.itemID = Integer.parseInt(idBox.getText());
        } catch (NumberFormatException nfe) {
            Methods.s("Emptying Serum 207 by Default");
        }
        this.dispose();
        Variables.guiIsDone = true;
        Variables.timer = new org.powerbot.game.api.util.Timer(1000);
    }
}