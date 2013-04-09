package caa4444.boner;

import caa4444.boner.misc.Const;
import caa4444.boner.misc.Methods;
import caa4444.boner.misc.Variables;
import caa4444.boner.nodes.*;
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
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

@Manifest(authors = {"caa4444"}, name = "Mr. Boner", description = "Free Prayer XP: Picks up bones and buries them. Start anywhere with bones.", version = 1)
public class MrBoner extends ActiveScript implements PaintListener,
        MessageListener {

    public static Tree jobContainer = null;
    public static ArrayList<Node> jobs = new ArrayList<Node>();
    static Client client;
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        Task.sleep(100);
        Mouse.setSpeed(Speed.VERY_FAST);
        Variables.startTime = System.currentTimeMillis();
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
            Node[] nodes = {new WalkBones(), new RotateBones(), new GatherBones()};
            jobs.add(new BoneZ(nodes));
            jobs.add(new BuryBones());
            jobContainer = new Tree(jobs.toArray(new Node[jobs.size()]));
        }
        return 100;
    }

    final NumberFormat DF = DecimalFormat.getInstance();
    int gain = 0;
    int xpHourly = 0;
    String gainedExperience = "";
    String xpHour = "";
    String boneHour = "";


    @Override
    public void onRepaint(Graphics g1) {
        final Point mouse = Mouse.getLocation();
        final Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(antialiasing);

        gain = Skills.getExperience(Skills.PRAYER)
                - Variables.startingExperience;

        xpHourly = Methods.getPerHour(gain, Variables.startTime);

        gainedExperience = DF.format(Skills.getExperience(Skills.PRAYER)
                - Variables.startingExperience);
        xpHour = DF.format(xpHourly);
        boneHour = DF.format(Methods.getPerHour(Variables.bonesBuried, Variables.startTime));


        // -- Fill top bar
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) Game.getDimensions().getWidth(), 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.drawString("Run Time: " + Const.TIMER.toElapsedString(), 3, 12);
        g.drawString(
                String.format("Prayer Level: %d/%d",
                        Skills.getLevel(Skills.PRAYER), Variables.startingLevel),
                210, 12);
        g.drawString(String.format("Prayer Experience Gained (hr): %s (%s)",
                gainedExperience, xpHour), 420, 12);
        g.drawString(
                String.format("Bones Buried (hr): %s (%s)",
                        Variables.bonesBuried, boneHour),
                210, 25);
        // -- Mouse
        g.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        int x = mouse.x, y = mouse.y;
        g.drawLine(x, y - 10, x, y + 10);
        g.drawLine(x - 10, y, x + 10, y);

        // -- Status and label
        g.setColor(Color.PINK);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        g.drawString("Mr. Boner by caa4444", 5, 372);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.BLUE);
        g2.setFont(new Font("Garamond", Font.PLAIN, 14));
        g2.drawString("Status: " + Variables.status, 310, 522);

    }

    @Override
    public void messageReceived(MessageEvent msg) {
        if (msg.getMessage().contains("bury the bone")) Variables.bonesBuried++;
    }

}