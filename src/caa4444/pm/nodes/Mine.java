package caa4444.pm.nodes;

import caa4444.pm.misc.Methods;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;

public class Mine extends Node {

    @Override
    public boolean activate() {
        return Players.getLocal() != null && Players.getLocal().getAnimation() == -1
                && Players.getLocal().getInteracting() == null && !Players.getLocal().isMoving();
    }

    @Override
    public void execute() {

        if (SceneEntities.getAt(3175, 3368).getId() == 11956) {
            Methods.mineRock(1);
        } else if (SceneEntities.getAt(3175, 3366).getId() == 11955) {
            Methods.mineRock(0);
        } else {
            Methods.s("Waiting for Iron");
        }
    }
}