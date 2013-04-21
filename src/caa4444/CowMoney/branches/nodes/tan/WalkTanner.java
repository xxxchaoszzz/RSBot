package caa4444.cowmoney.branches.nodes.tan;

import caa4444.cowmoney.misc.Const;
import caa4444.cowmoney.misc.Methods;
import caa4444.cowmoney.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;

public class WalkTanner extends Node {
    @Override
    public boolean activate() {
        return NPCs.getNearest(Const.ID_TANNER) == null || NPCs.getNearest(Const.ID_TANNER).getLocation().distanceTo() > 4;
    }

    @Override
    public void execute() {
        Methods.s("Walking to Tanner");
        Walking.walk(Variables.tanTile);
        if (!Players.getLocal().isMoving() && Walking.getDestination().distance(Players.getLocal().getLocation()) > 6 &&
                (NPCs.getNearest(Const.ID_TANNER) == null || NPCs.getNearest(Const.ID_TANNER).getLocation().distanceTo() > 4)) {
            Task.sleep(500);
        }
        final Timer t = new Timer(2000);
        while (Players.getLocal().isMoving() && Walking.getDestination().distance(Players.getLocal().getLocation()) > 6 &&
                (NPCs.getNearest(Const.ID_TANNER) == null || NPCs.getNearest(Const.ID_TANNER).getLocation().distanceTo() > 4) && t.isRunning()) {
            Task.sleep(500);
        }
    }
}
