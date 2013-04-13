package caa4444.cowmoney.branches.nodes.hide;

import caa4444.cowmoney.misc.Const;
import caa4444.cowmoney.misc.Methods;
import caa4444.cowmoney.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;

public class WalkCows extends Node {
    @Override
    public boolean activate() {
        return !Const.COW_AREA.contains(Players.getLocal());
    }

    @Override
    public void execute() {
        Methods.s("Walking to cows");
        Walking.walk(Variables.cowTile);
        Timer t = new Timer(500);
        while (!Players.getLocal().isMoving() && Walking.getDestination().distance(Players.getLocal().getLocation()) > 6 &&
                !Const.COW_AREA.contains(Players.getLocal()) && t.isRunning()) {
            Task.sleep(500);
        }
        t = new Timer(2000);
        while (Players.getLocal().isMoving() && Walking.getDestination().distance(Players.getLocal().getLocation()) > 6 &&
                !Const.COW_AREA.contains(Players.getLocal()) && t.isRunning()) {
            Task.sleep(500);
        }
    }
}
