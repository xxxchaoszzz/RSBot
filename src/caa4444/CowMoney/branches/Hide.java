package caa4444.cowmoney.branches;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

public class Hide extends Branch {

    public Hide(Node[] nodes) {
        super(nodes);
    }

    public static boolean looted;

    @Override
    public boolean branch() {
        if (!Inventory.isFull()) {
            looted = false;
            return true;
        }
        return false;
    }

}
