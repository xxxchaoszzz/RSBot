package caa4444.CowMoney.branches.nodes.bank;

import caa4444.CowMoney.misc.Const;
import caa4444.CowMoney.misc.Methods;
import caa4444.CowMoney.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;

public class WalkBank extends Node {
    @Override
    public boolean activate() {
        return Const.BANK_T.distance(Players.getLocal().getLocation()) > 4;
    }

    @Override
    public void execute() {
        Methods.s("Walking to Bank");
        Walking.walk(Variables.bankTile);
        Timer t = new Timer(500);
        while (!Players.getLocal().isMoving() && Walking.getDestination().distance(Players.getLocal().getLocation()) > 6 &&
                Const.BANK_T.distance(Players.getLocal().getLocation()) > 4 && t.isRunning())
            Task.sleep(500);
        t = new Timer(2000);
        while (Players.getLocal().isMoving() && Walking.getDestination().distance(Players.getLocal().getLocation()) > 6 &&
                Const.BANK_T.distance(Players.getLocal().getLocation()) > 4 && t.isRunning())
            Task.sleep(500);
    }
}
