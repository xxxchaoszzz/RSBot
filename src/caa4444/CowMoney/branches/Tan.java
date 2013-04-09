package caa4444.CowMoney.branches;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

public class Tan extends Branch {
    public Tan(Node[] nodes) {
        super(nodes);
    }

    @Override
    public boolean branch() {
        return Inventory.isFull() && Inventory.contains(1739);
    }
}
