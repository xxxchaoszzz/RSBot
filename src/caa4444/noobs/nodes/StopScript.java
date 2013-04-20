package caa4444.noobs.nodes;

import caa4444.noobs.misc.Methods;
import caa4444.noobs.misc.Variables;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;

public class StopScript extends LoopTask {

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 2500;
        }
        if (!Players.getLocal().isMoving() &&
                Players.getLocal().getLocation().distance(Variables.startLoc) > 8 && NPCs.getNearest(Variables.npcs) == null) {
            if (Walking.walk(Variables.startLoc)) {
                Methods.s("Walking to StartLoc");
            } else {
                Methods.stopScript("Out of Bounds");
            }
        }
        if (Players.getLocal().getHealthPercent() < 5) {
            Methods.stopScript("Low HP");
        }
        return 1000;
    }
}
