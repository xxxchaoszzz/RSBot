package caa4444.boner.nodes;

import caa4444.boner.misc.Variables;
import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;

public class BoneZ extends Branch {


    public BoneZ(Node[] nodes) {
        super(nodes);
    }

    @Override
    public boolean branch() {
        Variables.bone = GroundItems.getNearest(995, 532, 526);
        return Variables.bone != null && !Inventory.isFull() && Variables.bone.getLocation().canReach();
    }
}
