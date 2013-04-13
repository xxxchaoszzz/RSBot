package caa4444.CowMoney.branches.nodes.hide;

import caa4444.CowMoney.misc.Const;
import caa4444.CowMoney.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;

public class Collect extends Node {

    @Override
    public boolean activate() {
        return Const.COW_AREA.contains(Players.getLocal().getLocation()) && (GroundItems.getLoaded(12, 1739).length >= 2 || GroundItems.getLoaded(4, 1739).length > 0);
    }

    @Override
    public void execute() {
        GroundItem hide = GroundItems.getNearest(12, 1739);
        if (hide != null) {
            if (!hide.isOnScreen() && Methods.turnTo(hide, 20)) {
                Methods.s("Turning to hide");
                return;
            }
            if (hide.isOnScreen() && hide.interact("Take", "Cowhide")) {
                Methods.s("Taking hide");
                Tile hidTil = hide.getLocation();
                Timer wait = new Timer(3000);
                while (Players.getLocal().getLocation().distance(hidTil) > 0.75 && hide.validate() && wait.isRunning()) {
                    Task.sleep(50);
                }
            }
        }
    }
}