package caa4444.pm.nodes;

import caa4444.pm.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.tab.Inventory;

public class Drop extends Node {

    @Override
    public boolean activate() {
        return Inventory.getCount(false) > 26;
    }

    @Override
    public void execute() {
        Methods.s("Dropping Iron");
        while (Widgets.get(137, 56).getTextColor() == 0x0000FF) {
            Keyboard.sendKey('\u001B');
            Task.sleep(50);
        }
        while (Inventory.getCount(440) > 1) {
            Keyboard.sendText("2", false, 50, 50);
        }
    }
}

