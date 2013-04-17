package caa4444.cowmoney.branches.nodes.hide;

import caa4444.cowmoney.misc.Const;
import caa4444.cowmoney.misc.Methods;
import caa4444.cowmoney.misc.Variables;
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
        return Const.COW_AREA.contains(Players.getLocal().getLocation()) &&
                (GroundItems.getLoaded(4, 1739).length > 0 || GroundItems.getLoaded(12, 1739).length >= 2
                        || !Variables.killCows && GroundItems.getNearest(1739) != null);
    }

    @Override
    public void execute() {
        final GroundItem hide = GroundItems.getNearest(12, 1739);
        if (hide != null) {
            if (!hide.isOnScreen() && Methods.turnTo(hide, 20)) {
                Methods.s("Turning to hide");
                return;
            }
            if (hide.isOnScreen() && hide.interact("Take", "Cowhide")) {
                Methods.s("Taking hide");
                final Tile hideTile = hide.getLocation();
                final Timer wait = new Timer(3000);
                while (Players.getLocal().getLocation().distance(hideTile) > 0.75 && hide.validate() && wait.isRunning()) {
                    Task.sleep(50);
                }
            }
        }
    }
}