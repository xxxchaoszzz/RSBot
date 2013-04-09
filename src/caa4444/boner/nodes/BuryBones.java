package caa4444.boner.nodes;

import caa4444.boner.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;

import java.awt.*;

public class BuryBones extends Node {
    @Override
    public boolean activate() {
        return Inventory.isFull() || GroundItems.getNearest(532, 526) == null;
    }

    @Override
    public void execute() {
        if (Inventory.contains(532, 526)) {
            Methods.s("Burying Bones");
            for (Item a : Inventory.getItems(new Filter<Item>() {
                @Override
                public boolean accept(Item item) {
                    return item.getName().contains("Bone");
                }
            })) {
                if (a != null) {
                    final Point p = a.getWidgetChild().getCentralPoint();
                    Mouse.click(p.x + Random.nextInt(-7, 7),
                            p.y + Random.nextInt(-7, 7), true);
                }
                Task.sleep(300, 400);
            }
        }

    }
}
