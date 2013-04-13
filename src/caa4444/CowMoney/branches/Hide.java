package caa4444.cowmoney.branches;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

public class Hide extends Branch {

    public Hide(Node[] nodes) {
        super(nodes);
    }

    @Override
    public boolean branch() {
        return !Inventory.isFull();
    }
}
