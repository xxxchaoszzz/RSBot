package caa4444.noobs;

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
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Manifest(authors = {"caa4444"}, name = "Slaughterhouse",
        description = "Aggressive AIO power-fighter. Put abilities on slots 2-7", version = 1.1)
public class Slaughterhouse extends ActiveScript implements PaintListener {

    private static Tree jobContainer;
    private static final Node[] jobs = {new Attack(), new Chain()};
    private static Client client;
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        final GUI gui = new GUI();
        gui.setVisible(true);
        while (gui.isVisible()) {
            sleep(100);
        }
        Task.sleep(100);
        Mouse.setSpeed(Mouse.Speed.VERY_FAST);
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
        }
        return 100;
    }

    private final NumberFormat df = DecimalFormat.getInstance();
    private final Dimension game = Game.getDimensions();

    @Override
    public void onRepaint(Graphics g1) {
        final Point mouse = Mouse.getLocation();
        final Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(antialiasing);

        // -- Fill top bar
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, game.width, 50);

        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.drawString("Run Time: " + Variables.timer.toElapsedString(), 3, 12);

        int row = 0;
        for (int i = 0; i < skill.values().length; i++) {
            if (Methods.xpGained(skill.values()[i].getId()) > 0) {
                g.drawString(
                        String.format(skill.values()[i].getName() + " Level: %d/%d",
                                Skills.getLevel(skill.values()[i].getId()), Methods.startLv(skill.values()[i].getId())),
                        123, 12 + 13 * row);
                g.drawString(String.format(skill.values()[i].getName() + " Experience Gained (hr): %s (%s)",
                        df.format(Methods.xpGained(skill.values()[i].getId())), df.format(Methods.getPerHour(skill.values()[i].getId()))), 273, 12 + 13 * row);
                g.drawString(skill.values()[i].getName() + " TTL: " + Time.format(Methods.TTL(skill.values()[i].getId())), 583, 12 + 13 * row);
                row++;
            }
        }

        // -- Mouse
        g.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        final int x = mouse.x;
        final int y = mouse.y;
        g.drawLine(x, y - 10, x, y + 10);
        g.drawLine(x - 10, y, x + 10, y);

        // -- Status and label
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        g.drawString("Slaughterhouse by caa4444", 5, 310);

        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Garamond", Font.PLAIN, 14));
        g2.drawString("Status: " + Variables.status, 310, 522);
    }
}

