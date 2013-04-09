package caa4444.boner.nodes;

import caa4444.boner.misc.Methods;
import caa4444.boner.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;

public class GatherBones extends Node {


    @Override
    public boolean activate() {
        return Variables.bone.validate() && !Players.getLocal().isMoving();
    }

    @Override
    public void execute() {
        Methods.s("Taking Bones");
        switch (Variables.bone.getId()) {
            case 526:
                Variables.bone.interact("Take", "Bones");
                break;
            case 532:
                Variables.bone.interact("Take", "Big Bones");
                break;
            case 995:
                Variables.bone.interact("Take", "Coins");
                break;
        }
        Task.sleep(300, 500);
    }
}