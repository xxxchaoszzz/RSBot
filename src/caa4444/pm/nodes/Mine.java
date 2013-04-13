package caa4444.pm.nodes;

import caa4444.pm.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;

public class Mine extends Node {

    @Override
    public boolean activate() {
        return Players.getLocal() != null && Players.getLocal().getAnimation() == -1
                && Players.getLocal().getInteracting() == null && !Players.getLocal().isMoving();
    }

    @Override
    public void execute() {

        if (SceneEntities.getAt(3175, 3368).getId() == 11956 && SceneEntities.getAt(3175, 3366).getId() == 11955) {
            switch (Random.nextInt(1, 3) % 2) {
                case 1:
                    Methods.mineRock(1);
                    break;
                case 0:
                    Methods.mineRock(0);
                    break;
                default:
                    Task.sleep(10);
            }
        } else if (SceneEntities.getAt(3175, 3368).getId() == 11956 && SceneEntities.getAt(3175, 3366).getId() == 11556) {
            Methods.mineRock(1);
        } else if (SceneEntities.getAt(3175, 3368).getId() != 11557 || SceneEntities.getAt(3175, 3366).getId() != 11955) {
            Methods.s("Waiting for Iron");
            while (SceneEntities.getAt(3175, 3368).getId() == 11557 && SceneEntities.getAt(3175, 3366).getId() == 11556) {
                Task.sleep(10);
            }
        } else {
            Methods.mineRock(0);
        }
    }
}