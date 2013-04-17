package pro.geektalk.alcher;

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
import org.powerbot.game.api.util.Time;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;
import pro.geektalk.alcher.loops.Antiban;
import pro.geektalk.alcher.loops.StopScript;
import pro.geektalk.alcher.misc.Methods;
import pro.geektalk.alcher.misc.Variables;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

@Manifest(authors = {"caa4444"}, name = "EZ Alch", description = "A task based high alchemy script", version = 1.01, vip = true)
public class Alcher extends ActiveScript implements PaintListener,
        MessageListener {

    public static Tree jobContainer;
    public static ArrayList<Node> jobs = new ArrayList<Node>();
    static Client client;
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        final GUI gui = new GUI();
        gui.setVisible(true);
        while (gui.isVisible()) {
            Task.sleep(100);
        }
        Mouse.setSpeed(Mouse.Speed.FAST);
        getContainer().submit(new StopScript());
        getContainer().submit(new Antiban());
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
                final Node node = jobContainer.state();
                if (node != null) {
                    jobContainer.set(node);
                    getContainer().submit(node);
                    node.join();
                }
            } else {
                for (int i = 0; i < 28; i++) {
                    if (Variables.slotsToAlch[i]) {
                        jobs.add(new AlchItem(i));
                    }
                }
                jobContainer = new Tree(jobs.toArray(new Node[jobs.size()]));
            }
        }
        return 100;
    }

    NumberFormat df = DecimalFormat.getInstance();
    int gain;

    @Override
    public void onRepaint(Graphics g1) {
        final Point mouse = Mouse.getLocation();
        final Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(antialiasing);

        gain = Skills.getExperience(Skills.MAGIC) - Variables.startingExperience;

        // -- Fill top bar
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) Game.getDimensions().getWidth(), 50);

        g.setColor(Color.GRAY);
        g.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
        g.drawString("Run Time: " + Variables.timer.toElapsedString(), 3, 12);
        g.drawString("Experience Gained (hr): " + df.format(gain) + " (" + df.format(Methods.getPerHour(gain)) + ")", 3, 25);
        g.drawString("Alchs (hr): " + df.format(Variables.alchs) + " (" + df.format(Methods.getPerHour(Variables.alchs)) + ")", 3, 38);
        g.drawString("Level info: " + Skills.getLevel(Skills.MAGIC) + "/" + Variables.startingLevel, 220, 12);
        g.drawString("TTL: " + Time.format(Methods.TTL(Skills.MAGIC)), 583, 12);
        g.drawString("Alching Item: " + Variables.slot, 220, 25);
        g.drawString("Coins Made (hr): " + df.format(Variables.coinsMade) + " (" + df.format(Methods.getPerHour(Variables.coinsMade)) + ")", 220, 38);

        // -- Mouse
        g.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        final int x = mouse.x;
        final int y = mouse.y;
        g.drawLine(x, y - 10, x, y + 10);
        g.drawLine(x - 10, y, x + 10, y);

        // -- Status and label
        g.setColor(Color.GRAY);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        g.drawString("EZ Alch by caa4444", 5, 310);

        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.BLUE);
        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        g2.drawString("Status: " + Variables.status, 310, 522);

    }

    @Override
    public void messageReceived(MessageEvent msg) {
        final String m = msg.getMessage();
        if (m.toLowerCase().contains("have been added ")) {
            Variables.alchs++;
            String text = m.substring(0, m.indexOf("co") - 1);
            if (text.contains(",")) {
                text = text.replaceAll(",", "");
            }
            Variables.coinsMade += Integer.parseInt(text);
        }
    }
}