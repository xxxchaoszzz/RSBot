package caa4444.hideCraft;

import caa4444.hideCraft.misc.Const;
import caa4444.hideCraft.misc.Variables;
import caa4444.hideCraft.nodes.BankStuff;
import caa4444.hideCraft.nodes.Loop;
import caa4444.hideCraft.nodes.MakeLeather;
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
        GUI gui = new GUI();
        gui.setVisible(true);
        while (gui.isVisible()) {
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
                Node job = jobContainer.state();
                if (job != null) {
                    jobContainer.set(job);
                    getContainer().submit(job);
                    job.join();
                }
            } else {
                jobContainer = new Tree(new Node[]{new MakeLeather(), new BankStuff()});
            }
        }
        return 300;
    }


    @Override
    public void onRepaint(Graphics g1) {
        Point mouse = Mouse.getLocation();
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(antialiasing);


        // -- Fill top bar
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) Game.getDimensions().getWidth(), 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.drawString("Run Time: " + Variables.timer.toElapsedString(), 3, 12);
        g.drawString("Making: " + Variables.i.getITEM_NAME(), 3, 25);
        g.drawString("Crafting Level: " + Skills.getLevel(Skills.CRAFTING) + "/" + Variables.startingLevel,
                210, 12);
        g.drawString("Crafting Experience Gained (hr): " + Variables.xpGain + " (" + Variables.xpHour + ")", 420, 12);
        g.drawString("Items Made (hr): " + Variables.itemsMade + " (" + Variables.itemHour + ")",
                210, 25);
        g.drawString("TTL: " + Variables.TTL,
                420, 25);
        // -- Mouse
        g.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        int x = mouse.x;
        int y = mouse.y;
        g.drawLine(x, y - 10, x, y + 10);
        g.drawLine(x - 10, y, x + 10, y);

        // -- Status and label
        g.setColor(Color.PINK);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        g.drawString("Hard Leather Sweatshop by caa4444", 5, 372);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.BLUE);
        g2.setFont(new Font("Garamond", Font.PLAIN, 14));
        g2.drawString("Status: " + Variables.status, 310, 522);

    }

    @Override
    public void messageReceived(MessageEvent msg) {
        if (msg.getMessage().contains("make this item.")) {
            Variables.itemsMade++;
            Const.TIMER.reset();
        }
    }

}