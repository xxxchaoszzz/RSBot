package caa4444.cowmoney;

import caa4444.cowmoney.branches.Bank;
import caa4444.cowmoney.branches.Hide;
import caa4444.cowmoney.branches.Tan;
import caa4444.cowmoney.branches.nodes.bank.BankLeather;
import caa4444.cowmoney.branches.nodes.bank.WalkBank;
import caa4444.cowmoney.branches.nodes.hide.Attack;
import caa4444.cowmoney.branches.nodes.hide.Collect;
import caa4444.cowmoney.branches.nodes.hide.WalkCows;
import caa4444.cowmoney.branches.nodes.tan.TanHides;
import caa4444.cowmoney.branches.nodes.tan.WalkTanner;
import caa4444.cowmoney.misc.Const;
import caa4444.cowmoney.misc.Loots;
import caa4444.cowmoney.misc.Methods;
import caa4444.cowmoney.misc.Variables;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Manifest(authors = {"caa4444"}, name = "CowMoney", description = "AIO Burthorpe cow looter/hide tanner",
        version = 1.25, singleinstance = false, vip = true)
public class CowMoney extends ActiveScript implements PaintListener {

    private static Tree jobContainer;
    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
    private static Client client;
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        final GUI gui = new GUI();
        gui.setVisible(true);
        while (gui.isVisible()) {
            Task.sleep(100);
        }
        Task.sleep(100);
        Mouse.setSpeed(Variables.speed);
        getContainer().submit(new StopScript());
        final ArrayList<Node> hideBranch = new ArrayList<Node>();
        hideBranch.add(new WalkCows());
        for (Loots l : Variables.loots) {
            if (l == Loots.Cowhides) {
                Variables.tan = true;
            }
            hideBranch.add(new Collect(l.getITEM_ID()));
        }
        if (Variables.killCows) {
            hideBranch.add(new Attack());
        }
        final Node[] lootItems = hideBranch.toArray(new Node[1]);

        final Hide hide = new Hide(lootItems);
        provide(hide);

        if (Variables.tan) {
            provide(new Tan(new Node[]{new WalkTanner(), new TanHides()}));
        }
        provide(new Bank(new Node[]{new WalkBank(), new BankLeather()}));
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
        g.drawString("Run Time: " + Const.TIMER.toElapsedString(), 3, 12);
        g.drawString("Hides Collected (hr): " + df.format(Variables.hidesR) + " (" + df.format(Methods.getPerHour(Variables.hidesR)) + ")", 210, 12);
        g.drawString("Bones Collected (hr): " + df.format(Variables.bonesR) + " (" + df.format(Methods.getPerHour(Variables.bonesR)) + ")", 210, 25);
        g.drawString("Raw Beef Collected (hr): " + df.format(Variables.beefR) + " (" + df.format(Methods.getPerHour(Variables.beefR)) + ")", 210, 38);
        g.drawString("Profit (hr): " + df.format(Variables.profitR) + " (" + df.format(Methods.getPerHour(Variables.profitR)) + ")", 420, 12);

        // -- Mouse
        g.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        final int x = mouse.x;
        final int y = mouse.y;
        g.drawLine(x, y - 10, x, y + 10);
        g.drawLine(x - 10, y, x + 10, y);

        // -- Status and label
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        g.drawString("CowMoney by caa4444", 5, 372);


        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Garamond", Font.PLAIN, 14));
        g2.drawString("Status: " + Variables.status, 310, 522);
    }

    final synchronized void provide(Node... jobs) {
        for (Node job : jobs) {
            if (!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }
}

