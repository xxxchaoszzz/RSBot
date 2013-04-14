package caa4444.hidecraft;

import caa4444.hidecraft.misc.Const;
import caa4444.hidecraft.misc.Variables;
import caa4444.hidecraft.nodes.BankStuff;
import caa4444.hidecraft.nodes.Loop;
import caa4444.hidecraft.nodes.MakeLeather;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import java.awt.*;

@Manifest(authors = {"caa4444"}, name = "Hard Leather Sweatshop", description = "Crafts Hard leather items, start with thread in inventory", version = 1)
public class hideCraft extends ActiveScript implements PaintListener,
        MessageListener {

    public static Tree jobContainer;
    static Client client;
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        final GUI GUI = new GUI();
        GUI.setVisible(true);
        while (GUI.isVisible()) {
            Task.sleep(100);
        }
        Task.sleep(100);
        Mouse.setSpeed(Mouse.Speed.VERY_FAST);
        getContainer().submit(new Loop());
    }

    @Override
    public int loop() {
        if (Variables.guiIsDone) {
            if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
                return 2500;
            }
            if (client != Context.client()) {
                WidgetCache.purge();
                Context.get().getEventManager().addListener(this);
                client = Context.client();
            }
            if (jobContainer != null) {
                final Node JOB = jobContainer.state();
                if (JOB != null) {
                    jobContainer.set(JOB);
                    getContainer().submit(JOB);
                    JOB.join();
                }
            } else {
                jobContainer = new Tree(new Node[]{new MakeLeather(), new BankStuff()});
            }
        }
        return 300;
    }


    @Override
    public void onRepaint(Graphics g1) {
        final Point MOUSE = Mouse.getLocation();
        final Graphics2D G = (Graphics2D) g1;
        G.setRenderingHints(antialiasing);


        // -- Fill top bar
        G.setColor(Color.BLACK);
        G.fillRect(0, 0, (int) Game.getDimensions().getWidth(), 50);

        G.setColor(Color.WHITE);
        G.setFont(new Font("Arial", Font.BOLD, 11));
        G.drawString("Run Time: " + Variables.timer.toElapsedString(), 3, 12);
        G.drawString("Making: " + Variables.i.getITEM_NAME(), 3, 25);
        G.drawString("Crafting Level: " + Skills.getLevel(Skills.CRAFTING) + "/" + Variables.startingLevel,
                210, 12);
        G.drawString("Crafting Experience Gained (hr): " + Variables.xpGain + " (" + Variables.xpHour + ")", 420, 12);
        G.drawString("Items Made (hr): " + Variables.itemsMade + " (" + Variables.itemHour + ")",
                210, 25);
        G.drawString("TTL: " + Variables.TTL,
                420, 25);
        // -- Mouse
        G.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        final int X = MOUSE.x;
        final int Y = MOUSE.y;
        G.drawLine(X, Y - 10, X, Y + 10);
        G.drawLine(X - 10, Y, X + 10, Y);

        // -- Status and label
        G.setColor(Color.PINK);
        G.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        G.drawString("Hard Leather Sweatshop by caa4444", 5, 372);

        final Graphics2D G2 = (Graphics2D) G.create();
        G2.setColor(Color.BLUE);
        G2.setFont(new Font("Garamond", Font.PLAIN, 14));
        G2.drawString("Status: " + Variables.status, 310, 522);

    }

    @Override
    public void messageReceived(MessageEvent msg) {
        if (msg.getMessage().contains("make this item.")) {
            Variables.itemsMade++;
            Const.TIMER.reset();
        }
    }

}