package pro.geektalk.alcher;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import pro.geektalk.alcher.misc.Const;
import pro.geektalk.alcher.misc.Methods;
import pro.geektalk.alcher.misc.Variables;

import java.awt.*;

public class AlchItem extends Node {

    @Override
    public boolean activate() {
        return Methods.inventoryContains(Const.NATURE_RUNE)
                && Methods.isSlotOccupied(SLOT);
    }

    public AlchItem(int i) {
        SLOT = i;
    }

    private final int SLOT;

    @Override
    public void execute() {
        if (!Widgets.get(640, 4).visible())
            Widgets.get(640, 3).click(true);
        while (Widgets.get(137, 56).getTextColor() == 0x0000FF) {
            Keyboard.sendKey('\u001B');
            Task.sleep(10, 40);
        }
        Variables.slot = SLOT + 1;

        final WidgetChild WIDGET = Widgets.get(Const.WIDGET_TEXT,
                Const.WIDGETCHILD_TEXT).getChild(0);
        final Item ITEM = Inventory.getItemAt(SLOT);
        if (ITEM != null) {
            final Point P = ITEM.getWidgetChild().getCentralPoint();
            if (!ITEM.getWidgetChild().getBoundingRectangle().contains(Mouse.getLocation())) Mouse.move(P);
            Methods.s("Alching " + ITEM.getName());
            if (WIDGET.getText().contains("ast")) {
                if (Random.nextInt(0, 10) % 2 == 0) {
                    Mouse.click(P.x + Random.nextInt(-7, 7),
                            P.y + Random.nextInt(-7, 7), true);
                } else {
                    Mouse.click(P, true);
                }
                Task.sleep(1750, 18950);
            } else {
                Keyboard.sendText("0", false);
                Mouse.click(ITEM.getWidgetChild().getCentralPoint(), true);
                Task.sleep(1750, 1850);
            }
        }
    }
}
