package caa4444.boner.nodes;

import caa4444.pm.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;

public class BuryActionBar extends Node {

    @Override
    public boolean activate() {
        return Inventory.isFull() || GroundItems.getNearest(532, 526) == null;
    }

    @Override
    public void execute() {
        Methods.s("Burying Bones");
        if (!Widgets.get(640, 4).visible()) {
            Widgets.get(640, 3).click(true);
        }
        while (Widgets.get(137, 56).getTextColor() == 0x0000FF) {
            Keyboard.sendKey('\u001B');
            Task.sleep(50);
        }
        while (Inventory.getCount(440) > 1) {
            Keyboard.sendText("2", false, 50, 50);
        }
        if (Widgets.get(640, 4).isOnScreen()) {
            Widgets.get(640, 30).click(true);
        }
    }
}

