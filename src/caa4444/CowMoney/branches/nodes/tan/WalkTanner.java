package caa4444.CowMoney.branches.nodes.tan;

import caa4444.CowMoney.misc.Const;
import caa4444.CowMoney.misc.Methods;
import caa4444.CowMoney.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;

public class WalkTanner extends Node {
    @Override
    public boolean activate() {
        return NPCs.getNearest(Const.ID_TANNER) == null || (NPCs.getNearest(Const.ID_TANNER) != null
                && NPCs.getNearest(Const.ID_TANNER).getLocation().distance(Players.getLocal()) > 4);
    }

    @Override
    public void execute() {
        Methods.s("Walking to Tanner");
        Walking.walk(Variables.tanTile);
        Timer t = new Timer(500);
        while (!Players.getLocal().isMoving() && Walking.getDestination().distance(Players.getLocal().getLocation()) > 6 &&
                NPCs.getNearest(Const.ID_TANNER) == null || (NPCs.getNearest(Const.ID_TANNER) != null
                && NPCs.getNearest(Const.ID_TANNER).getLocation().distance(Players.getLocal()) > 4) && t.isRunning())
            Task.sleep(500);
        t = new Timer(2000);
        while (Players.getLocal().isMoving() && Walking.getDestination().distance(Players.getLocal().getLocation()) > 6 &&
                NPCs.getNearest(Const.ID_TANNER) == null || (NPCs.getNearest(Const.ID_TANNER) != null
                && NPCs.getNearest(Const.ID_TANNER).getLocation().distance(Players.getLocal()) > 4) && t.isRunning())
            Task.sleep(500);
    }
}
