package caa4444.pm.nodes;

import caa4444.pm.misc.Methods;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;

public class Loop extends LoopTask {

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 2500;
        }
        if (Inventory.getCount(20396) > 0) {
            Methods.s("Teleporting Urn");
            Keyboard.sendText("1", false);
        }
        if (Inventory.getCount(440) > 0) {
            Methods.s("Dropping Iron");
            while (SceneEntities.getAt(3175, 3368).getId() == 11557 && SceneEntities.getAt(3175, 3366).getId() == 11556
                    && Inventory.getCount(440) > 1 && Widgets.get(137, 56).getTextColor() != 0x0000FF)
                Keyboard.sendText("2", false);
            return Random.nextInt(5000, 7501);
        }
        return 1000;
    }

}