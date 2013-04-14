package caa4444.cowmoney.branches.nodes.tan;

import caa4444.cowmoney.misc.Const;
import caa4444.cowmoney.misc.Methods;
import caa4444.cowmoney.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class TanHides extends Node {
    @Override
    public boolean activate() {
        return NPCs.getNearest(Const.ID_TANNER) != null
                && NPCs.getNearest(Const.ID_TANNER).getLocation().distance(Players.getLocal()) < 4;
    }

    @Override
    public void execute() {
        Methods.s("Interacting with tanner");
        final NPC TANNER = NPCs.getNearest(Const.ID_TANNER);
        if (!Widgets.get(1370).validate()) {
            if (TANNER == null) {
                return;
            }
            if (!TANNER.isOnScreen()) {
                Methods.turnTo(TANNER, 20);
            }
            TANNER.interact("Tan hide");
            final Timer T = new Timer(1000);
            while (!Widgets.get(1370).validate() && T.isRunning()) {
                Task.sleep(50);
            }
        }
        if (Widgets.get(1370).validate()) {
            Methods.s("Tanning Hide");
            Widgets.get(1370, 38).click(true);
            final Timer T = new Timer(1000);
            while (Inventory.contains(1739) && T.isRunning()) {
                Task.sleep(50);
            }
            Variables.hides += Inventory.getCount(1743);
        }
    }
}
