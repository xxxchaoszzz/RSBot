package caa4444.potEmpty.nodes;

import caa4444.potEmpty.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

public class EmptyPots extends Node {
    @Override
    public boolean activate() {
        return Inventory.contains(Variables.itemID) && !Widgets.get(762).validate();
    }

    @Override
    public void execute() {

        if (!Widgets.get(640, 4).visible()) {
            Widgets.get(640, 3).click(true);
        }
        while (Widgets.get(137, 56).getTextColor() == 0x0000FF) {
            Keyboard.sendKey('\u001B');
            Task.sleep(10, 40);
        }

        Variables.timer.reset();

        while (Variables.timer.isRunning() && Inventory.contains(Variables.itemID)) {
            Keyboard.sendText("1", false);
            Task.sleep(40, 60);
        }

        Bank.getNearest().hover();

    }
}
