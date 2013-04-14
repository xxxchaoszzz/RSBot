package caa4444.cowmoney.branches.nodes.hide;

import caa4444.cowmoney.misc.Const;
import caa4444.cowmoney.misc.Methods;
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
        final GroundItem HIDE = GroundItems.getNearest(12, 1739);
        if (HIDE != null) {
            if (!HIDE.isOnScreen() && Methods.turnTo(HIDE, 20)) {
                Methods.s("Turning to hide");
                return;
            }
            if (HIDE.isOnScreen() && HIDE.interact("Take", "Cowhide")) {
                Methods.s("Taking hide");
                final Tile HIDE_TILE = HIDE.getLocation();
                final Timer WAIT = new Timer(3000);
                while (Players.getLocal().getLocation().distance(HIDE_TILE) > 0.75 && HIDE.validate() && WAIT.isRunning()) {
                    Task.sleep(50);
                }
            }
        }
    }
}