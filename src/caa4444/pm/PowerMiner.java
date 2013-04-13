package caa4444.pm;

import caa4444.pm.misc.Const;
import caa4444.pm.misc.Methods;
import caa4444.pm.misc.Variables;
import caa4444.pm.nodes.Drop;
import caa4444.pm.nodes.Loop;
import caa4444.pm.nodes.Mine;
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

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

@Manifest(authors = {"caa4444"}, name = "Pumping Iron", description = "Powermining Iron in Varrock West", version = 1)
public class PowerMiner extends ActiveScript implements PaintListener,
        MessageListener {

    public static Tree jobContainer;
    public static ArrayList<Node> jobs = new ArrayList<Node>();
    static Client client;
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        Task.sleep(100);
        Mouse.setSpeed(Mouse.Speed.FAST);
        Variables.startTime = System.currentTimeMillis();
        Variables.iron = 0;
        Variables.startingMExperience = Skills.getExperience(Skills.MINING);
        Variables.startingMLevel = Skills.getLevel(Skills.MINING);
        getContainer().submit(new Loop());
    }

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 2500;
        }
        if (client != Context.client()) {
            WidgetCache.purge();
            Context.get().getEventManager().addListener(this);
            client = Context.client();
        }
        if (jobContainer != null) {
            final Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
            }
        } else {
            jobs.add(new Drop());
            jobs.add(new Mine());
            jobContainer = new Tree(jobs.toArray(new Node[jobs.size()]));
        }
        return 100;
    }

    @Override
    public void onRepaint(Graphics g1) {
        Point mouse = Mouse.getLocation();
        Graphics2D g = (Graphics2D) g1;
        Dimension game = Game.getDimensions();
        g.setRenderingHints(antialiasing);

        NumberFormat df = DecimalFormat.getInstance();
        int Mgain = Skills.getExperience(Skills.MINING)
                - Variables.startingMExperience;

        int xpMHourly = Methods.getPerHour(Mgain, Variables.startTime);
        long TTL = xpMHourly != 0 ? (long) ((double) Skills.getExperienceToLevel(Skills.MINING, Skills.getLevel(Skills.MINING) + 1) / (double) xpMHourly * 3600000) : 0;

        String gainedMExperience = df.format(Mgain);
        String xpMHour = df.format(xpMHourly);

        int ironHour = Methods.getPerHour(Variables.iron,
                Variables.startTime);

        String iron = df.format(Variables.iron);
        String ironHourly = df.format(ironHour);


        // -- Fill top bar
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) Game.getDimensions().getWidth(), 50);

        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.drawString("Run Time: " + Const.TIMER.toElapsedString(), 3, 12);
        g.drawString(
                String.format("Mining Level: %d/%d",
                        Skills.getLevel(Skills.MINING), Variables.startingMLevel),
                123, 25);
        g.drawString(String.format("Mining Experience Gained (hr): %s (%s)",
                gainedMExperience, xpMHour), 273, 25);
        g.drawString(String.format("Iron (hr): %s (%s)", iron, ironHourly),
                123, 12);
        g.drawString("TTL: " + Time.format(TTL), 273, 12);

        // -- Mouse
        g.setColor(Mouse.isPressed() ? Color.WHITE.brighter() : Color.BLACK
                .darker());
        g.drawLine(mouse.x + game.width, mouse.y, mouse.x - game.width, mouse.y);
        g.drawLine(mouse.x, mouse.y + game.height, mouse.x, mouse.y
                - game.height);

        // -- Status and label
        g.setColor(Color.PINK);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        g.drawString("Pumping Iron by caa4444", 5, 310);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.BLUE);
        g2.setFont(new Font("Garamond", Font.PLAIN, 14));
        g2.drawString("Status: " + Variables.status, 310, 522);

    }

    @Override
    public void messageReceived(MessageEvent msg) {
        String m = msg.getMessage();
        if (m.toLowerCase().contains("mine some iron")) {
            Variables.iron++;
        }
    }

}