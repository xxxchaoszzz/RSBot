package caa4444.cowmoney.branches;

import caa4444.cowmoney.misc.Variables;
import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

public class Bank extends Branch {
    public Bank(Node[] nodes) {
        super(nodes);
    }

    @Override
    public boolean branch() {
        return Inventory.isFull() && !Inventory.contains(1739) || !Variables.tan;
    }
}
