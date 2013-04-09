package caa4444.pm.nodes;

import caa4444.pm.misc.Const;
import caa4444.pm.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Mine extends Node {

    @Override
    public boolean activate() {
        return Players.getLocal() != null && Players.getLocal().getAnimation() == -1
                && Players.getLocal().getInteracting() == null && !Players.getLocal().isMoving();
    }

    @Override
    public void execute() {
        final SceneObject rockToMine = SceneEntities.getNearest(new Filter<SceneObject>() {
            @Override
            public boolean accept(SceneObject sceneObject) {
                for (int i : Const.ironRocks) if (sceneObject.getId() == i) return true;
                return false;
            }
        });
        final Tile rockLoc = rockToMine.getLocation();
        final SceneObject rock2 = SceneEntities.getNearest(new Filter<SceneObject>() {
            @Override
            public boolean accept(SceneObject sceneObject) {
                for (int i : Const.ironRocks)
                    if (sceneObject.getId() == i && !sceneObject.equals(rockToMine)) return true;
                return false;
            }
        });
        rockToMine.interact("Mine");
        Task.sleep(400, 500);
        Methods.s("Waiting for Iron");
        if (rock2 != null && rock2.getLocation().distance(Players.getLocal().getLocation()) <= 1) rock2.hover();
        Timer wait = new Timer(2000);
        while (SceneEntities.getAt(rockLoc).getId() == rockToMine.getId() && Players.getLocal().isIdle() && wait.isRunning())
            Task.sleep(50);
        while (SceneEntities.getAt(rockLoc).getId() == rockToMine.getId() && !Players.getLocal().isIdle() && wait.isRunning())
            Task.sleep(50);
        if (rock2 != null && rock2.getLocation().distance(Players.getLocal().getLocation()) > 1)
            rock2.getLocation().getLocation().click(true);
    }
}