package caa4444.hideCraft.nodes;

import caa4444.hideCraft.misc.Const;
import caa4444.hideCraft.misc.Methods;
import caa4444.hideCraft.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

public class MakeLeather extends Node {
    @Override
    public boolean activate() {
        return Inventory.contains(1743) && !Widgets.get(762).validate();
    }

    @Override
    public void execute() {
        if (!Inventory.contains(1734)) {
            Methods.stopScript("Out of Thread");
            return;
        }

        while (Inventory.contains(1743) && Const.TIMER.isRunning()) {
            Task.sleep(50);
        }

        if (Widgets.get(1371).validate()) {
            if (Widgets.get(1371, 44).getChild(Variables.i.getWIDGET_ID()).getTextureId() == -1) {
                Widgets.get(1371, 44).getChild(Variables.i.getWIDGET_ID()).click(true);
            }
            Methods.s("Crafting Leather");
            if (Widgets.get(1371, 44).getChild(Variables.i.getWIDGET_ID()).getTextureId() == 15201 &&
                    Widgets.get(1370, 20).interact("Make")) {
                Task.sleep(2000, 3000);
                Bank.getNearest().hover();
            }
        } else if (Widgets.get(1179).validate()) {
            Methods.s("Opening Craft Menu");
            if (Widgets.get(1179, 16).interact("Select")) {
                Variables.timer.reset();
                while (!Widgets.get(1371).validate() && Variables.timer.isRunning()) {
                    Task.sleep(50);
                }
            }
        } else {
            Methods.s("Opening Tool Menu");
            if (Inventory.getItem(1743).getWidgetChild().interact("Craft")) {
                Variables.timer.reset();
                while (!Widgets.get(1371).validate() && !Widgets.get(1179).validate() && Variables.timer.isRunning()) {
                    Task.sleep(50);
                }
            }
        }
    }
}
