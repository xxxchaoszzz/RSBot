package pro.geektalk.alcher.loops;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import pro.geektalk.alcher.misc.Const;
import pro.geektalk.alcher.misc.Methods;
import pro.geektalk.alcher.misc.Variables;

public class StopScript extends LoopTask {

    @Override
    public int loop() {
        if (Variables.guiIsDone) {
            if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
                return 2500;
            }
            if (Methods.outOfItems()) {
                Methods.stopScript("Out of items");
            }
            if (!Methods.inventoryContains(Const.NATURE_RUNE)) {
                Methods.stopScript("Out of runes");
            }
        }
        return 1000;
    }
}
