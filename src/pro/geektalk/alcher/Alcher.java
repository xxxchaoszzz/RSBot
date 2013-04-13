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

@Manifest(authors = {"caa4444"}, name = "EZ Alch", description = "A task based high alchemy script", version = 1.00)
public class Alcher extends ActiveScript implements PaintListener,
        MessageListener {

    public static Tree jobContainer = null;
    public static ArrayList<Node> jobs = new ArrayList<Node>();
    static Client client;
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        final GUI GUI = new GUI();
        GUI.setVisible(true);
        while (GUI.isVisible()) {
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
                final Node GUI = jobContainer.state();
                if (GUI != null) {
                    jobContainer.set(GUI);
                    getContainer().submit(GUI);
                    GUI.join();
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
        final Point MOUSE = Mouse.getLocation();
        final Graphics2D G = (Graphics2D) g1;
        G.setRenderingHints(antialiasing);

        gain = Skills.getExperience(Skills.MAGIC) - Variables.startingExperience;

        // -- Fill top bar
        G.setColor(Color.BLACK);
        G.fillRect(0, 0, (int) Game.getDimensions().getWidth(), 50);

        G.setColor(Color.GRAY);
        G.setFont(new Font("Comic Sans MS", Font.ITALIC, 11));
        G.drawString("Run Time: " + Variables.timer.toElapsedString(), 3, 12);
        G.drawString("Experience Gained (hr): " + df.format(gain) + " (" + df.format(Methods.getPerHour(gain)) + ")", 3, 25);
        G.drawString("Alchs (hr): " + df.format(Variables.alchs) + " (" + df.format(Methods.getPerHour(Variables.alchs)) + ")", 3, 38);
        G.drawString("Level info: " + Skills.getLevel(Skills.MAGIC) + "/" + Variables.startingLevel, 220, 12);
        G.drawString("TTL: " + Time.format(Methods.TTL(Skills.MAGIC)), 583, 12);
        G.drawString("Alching Item: " + Variables.slot, 220, 25);
        G.drawString("Coins Made (hr): " + df.format(Variables.coinsMade) + " (" + df.format(Methods.getPerHour(Variables.coinsMade)) + ")", 220, 38);

        // -- Mouse
        G.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        final int X = MOUSE.x;
        final int Y = MOUSE.y;
        G.drawLine(X, Y - 10, X, Y + 10);
        G.drawLine(X - 10, Y, X + 10, Y);

        // -- Status and label
        G.setColor(Color.GRAY);
        G.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        G.drawString("EZ Alch by caa4444", 5, 310);

        final Graphics2D G2 = (Graphics2D) G.create();
        G2.setColor(Color.BLUE);
        G2.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        G2.drawString("Status: " + Variables.status, 310, 522);

    }

    @Override
    public void messageReceived(MessageEvent msg) {
        final String M = msg.getMessage();
        if (M.toLowerCase().contains("have been added ")) {
            Variables.alchs++;
            String text = M.substring(0, M.indexOf("co") - 1);
            if (text.contains(",")) {
                text = text.replaceAll(",", "");
            }
            Variables.coinsMade += Integer.parseInt(text);
        }
    }
}