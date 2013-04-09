package caa4444.CowMoney.branches;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

public class Bank extends Branch {
    public Bank(Node[] nodes) {
        super(nodes);
    }

    @Override
    public boolean branch() {
        return Inventory.getCount() > 0 && !Inventory.contains(1739);
    }
}
