package caa4444.cowmoney.branches.nodes.hide;

import caa4444.cowmoney.branches.Hide;
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
        return !Hide.looted && Const.COW_AREA.contains(Players.getLocal().getLocation()) &&
                (GroundItems.getLoaded(4, lootID).length >= 1 || GroundItems.getLoaded(12, lootID).length >= 2
                        || !Variables.killCows && GroundItems.getNearest(lootID) != null);
    }

    private final int lootID;
    private final String lootName;

    public Collect(int i) {
        lootID = i;
        switch (lootID) {
            case 2132:
                lootName = "Raw beef";
                break;
            case 526:
                lootName = "Bones";
                break;
            default:
                lootName = "Cowhide";
        }
    }

    @Override
    public void execute() {
        final GroundItem loot = GroundItems.getNearest(12, lootID);

        if (loot != null) {
            Hide.looted = true;

            if (!loot.isOnScreen() && Methods.turnTo(loot, 20)) {
                Methods.s("Turning to " + lootName);
                return;
            }
            if (loot.isOnScreen() && loot.interact("Take", lootName)) {
                Methods.s("Taking " + lootName);
                final Tile hideTile = loot.getLocation();
                final Timer wait = new Timer(3000);
                while (Players.getLocal().getLocation().distance(hideTile) > 0.75 && loot.validate() && wait.isRunning()) {
                    Task.sleep(50);
                }
            }
        }
    }
}