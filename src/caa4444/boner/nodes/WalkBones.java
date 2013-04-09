package caa4444.boner.nodes;

import caa4444.boner.misc.Methods;
import caa4444.boner.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;

public class WalkBones extends Node {

    @Override
    public boolean activate() {
        return Variables.bone.validate() && Players.getLocal().getLocation().distance(Variables.bone.getLocation()) > 8;
    }

    @Override
    public void execute() {
        Methods.s("Walking to Bones");
        Walking.walk(Variables.bone.getLocation());
        Timer t = new Timer(10000);
        while (Players.getLocal().isMoving() && t.isRunning()) Task.sleep(300);
    }
}