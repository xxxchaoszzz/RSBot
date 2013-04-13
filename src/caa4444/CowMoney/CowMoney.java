package caa4444.CowMoney;

import caa4444.CowMoney.branches.Bank;
import caa4444.CowMoney.branches.Hide;
import caa4444.CowMoney.branches.Tan;
import caa4444.CowMoney.branches.nodes.bank.BankLeather;
import caa4444.CowMoney.branches.nodes.bank.WalkBank;
import caa4444.CowMoney.branches.nodes.hide.Attack;
import caa4444.CowMoney.branches.nodes.hide.Collect;
import caa4444.CowMoney.branches.nodes.hide.WalkCows;
import caa4444.CowMoney.branches.nodes.tan.TanHides;
import caa4444.CowMoney.branches.nodes.tan.WalkTanner;
import caa4444.CowMoney.misc.Const;
import caa4444.CowMoney.misc.Methods;
import caa4444.CowMoney.misc.Variables;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Branch;
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

@Manifest(authors = {"caa4444"}, name = "CowMoney", description = "Burthope cow killer/hide tanner", version = 1)
public class CowMoney extends ActiveScript implements PaintListener {

    public static Tree jobContainer = null;
    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
    static Client client;
    private final RenderingHints ANTIALIASING = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public void onStart() {
        Task.sleep(100);
        Mouse.setSpeed(Mouse.Speed.VERY_FAST);
        getContainer().submit(new StopScript());
        provide(new Branch[]{new Hide(new Node[]{new WalkCows(), new Collect(), new Attack()}),
                new Tan(new Node[]{new WalkTanner(), new TanHides()}), new Bank(new Node[]{new WalkBank(), new BankLeather()})});
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
            Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
            }
        }
        return 100;
    }

    final NumberFormat DF = DecimalFormat.getInstance();
    final Dimension GAME = Game.getDimensions();

    @Override
    public void onRepaint(Graphics g1) {
        Point MOUSE = Mouse.getLocation();
        Graphics2D G = (Graphics2D) g1;
        G.setRenderingHints(ANTIALIASING);

        // -- Fill top bar
        G.setColor(Color.BLACK);
        G.fillRect(0, 0, GAME.width, 50);

        G.setColor(Color.GRAY);
        G.setFont(new Font("Arial", Font.BOLD, 11));
        G.drawString("Run Time: " + Const.TIMER.toElapsedString(), 3, 12);
        G.drawString("Hides Collected (hr): " + DF.format(Variables.hidesR) + " (" + DF.format(Methods.getPerHour(Variables.hidesR)) + ")", 210, 12);
        G.drawString("Profit (hr): " + DF.format(Variables.profitR) + " (" + DF.format(Methods.getPerHour(Variables.profitR)) + ")", 420, 12);

        // -- Mouse
        G.setColor(Mouse.isPressed() ? Color.YELLOW : Color.RED);
        int x = MOUSE.x;
        int y = MOUSE.y;
        G.drawLine(x, y - 10, x, y + 10);
        G.drawLine(x - 10, y, x + 10, y);

        // -- Status and label
        G.setColor(Color.WHITE);
        G.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        G.drawString("CowMoney by caa4444", 5, 372);


        Graphics2D g2 = (Graphics2D) G.create();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Garamond", Font.PLAIN, 14));
        g2.drawString("Status: " + Variables.status, 310, 522);
    }

    public final synchronized void provide(Node... jobs) {
        for (Node job : jobs) {
            if (!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }
}

