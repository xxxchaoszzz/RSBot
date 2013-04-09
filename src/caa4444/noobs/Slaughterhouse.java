package caa4444.noobs;

import caa4444.noobs.misc.Const;
import caa4444.noobs.misc.Methods;
import caa4444.noobs.misc.Variables;
import caa4444.noobs.nodes.Attack;
import caa4444.noobs.nodes.Chain;
import caa4444.noobs.nodes.StopScript;
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
import org.powerbot.game.api.util.Time;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Manifest(authors = {"caa4444"}, name = "Slaughterhouse", description = "Powertraining on low level critters", version = 1)
public class Slaughterhouse extends ActiveScript implements PaintListener {

    public static Tree jobContainer = null;
    public static Node[] jobs = {new Attack(), new Chain()};
    static Client client;
    private final RenderingHints ANTIALIASING = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        Task.sleep(100);
        Mouse.setSpeed(Speed.VERY_FAST);
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
            jobContainer = new Tree(jobs);
            getContainer().submit(new StopScript());
        }
        return 100;
    }

    final NumberFormat DF = DecimalFormat.getInstance();
    final Dimension GAME = Game.getDimensions();

    @Override
    public void onRepaint(Graphics g1) {
        final Point MOUSE = Mouse.getLocation();
        final Graphics2D G = (Graphics2D) g1;
        G.setRenderingHints(ANTIALIASING);

        // -- Fill top bar
        G.setColor(Color.BLACK);
        G.fillRect(0, 0, GAME.width, 50);

        G.setColor(Color.GRAY);
        G.setFont(new Font("Arial", Font.BOLD, 11));
        G.drawString("Run Time: " + Const.TIMER.toElapsedString(), 3, 12);

        int row = 0;
        for (int i = 0; i < skill.values().length; i++) {
            if (Methods.xpGained(skill.values()[i].getId()) > 0) {
                G.drawString(
                        String.format(skill.values()[i].getName() + " Level: %d/%d",
                                Skills.getLevel(skill.values()[i].getId()), Methods.startLv(skill.values()[i].getId())),
                        123, 12 + 13 * row);
                G.drawString(String.format(skill.values()[i].getName() + " Experience Gained (hr): %s (%s)",
                        DF.format(Methods.xpGained(skill.values()[i].getId())), DF.format(Methods.getPerHour(skill.values()[i].getId()))), 273, 12 + 13 * row);
                G.drawString(skill.values()[i].getName() + " TTL: " + Time.format(Methods.TTL(skill.values()[i].getId())), 583, 12 + 13 * row);
                row++;
            }
        }

        // -- Mouse
        G.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        int x = MOUSE.x, y = MOUSE.y;
        G.drawLine(x, y - 10, x, y + 10);
        G.drawLine(x - 10, y, x + 10, y);

        // -- Status and label
        G.setColor(Color.WHITE);
        G.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        G.drawString("Slaughterhouse by caa4444", 5, 310);

        Graphics2D g2 = (Graphics2D) G.create();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Garamond", Font.PLAIN, 14));
        g2.drawString("Status: " + Variables.status, 310, 522);
    }
}

