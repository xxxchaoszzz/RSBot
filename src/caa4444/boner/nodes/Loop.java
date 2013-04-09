package caa4444.boner.nodes;

import caa4444.boner.misc.Methods;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;

public class Loop extends LoopTask {

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 2500;
        }
        if (Walking.getEnergy() > Random.nextInt(30, 50) && !Walking.isRunEnabled()) {
            Methods.s("Enabling Run");
            Walking.setRun(true);
            return 4000;
        }
        if (Players.getLocal().getHealthPercent() < 10) {
            Methods.stopScript("Low HP");
        }
        return 3000;
    }

}