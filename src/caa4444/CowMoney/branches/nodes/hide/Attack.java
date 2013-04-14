package caa4444.cowmoney.branches.nodes.hide;

import caa4444.cowmoney.misc.Const;
import caa4444.cowmoney.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Attack extends Node {

    @Override
    public boolean activate() {
        return Const.COW_AREA.contains(Players.getLocal().getLocation()) && (GroundItems.getLoaded(12, 1739).length < 2 ||
                GroundItems.getLoaded(4, 1739).length == 0)
                && Players.getLocal() != null && Players.getLocal().getAnimation() == -1
                && !Players.getLocal().isMoving() && Players.getLocal().getInteracting() == null;
    }

    @Override
    public void execute() {
        final NPC NPC = NPCs.getNearest(new Filter<NPC>() {
            public boolean accept(final NPC npc) {
                return npc.getName().toLowerCase().contains("cow") && npc.getHealthPercent() > 0
                        && (!npc.isInCombat() || npc.getInteracting().equals(Players.getLocal()));
            }
        });
        if (NPC != null) {
            if (!NPC.isOnScreen() && Methods.turnTo(NPC, 20)) {
                Methods.s("Turning to " + NPC.getName());
                return;
            }
            if (NPC.interact("Attack", NPC.getName())) {
                Methods.s("Attacking " + NPC.getName());
                Timer wait = new Timer(600);
                while (Players.getLocal().getInteracting() == null && wait.isRunning()) {
                    Task.sleep(50);
                }
                wait = new Timer(1000);
                while (Players.getLocal().getInteracting() != null && wait.isRunning()) {
                    Task.sleep(50);
                }
            }
        }
    }
}