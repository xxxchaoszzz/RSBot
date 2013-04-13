package caa4444.noobs.nodes;

import caa4444.noobs.misc.Const;
import caa4444.noobs.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Attack extends Node {

    @Override
    public boolean activate() {
        return Players.getLocal() != null && !Players.getLocal().isMoving()
                && Players.getLocal().getAnimation() == -1 && Players.getLocal().getInteracting() == null;
    }

    @Override
    public void execute() {


        final NPC NPC = NPCs.getNearest(new Filter<NPC>() {
            public boolean accept(final NPC npc) {
                for (final String n : Const.NPCS) {
                    if (n.toLowerCase().contains(npc.getName().toLowerCase()) && npc.getHealthPercent() > 0
                            && (!npc.isInCombat() || npc.getInteracting().equals(Players.getLocal()))) {
                        return true;
                    }
                }
                return false;
            }
        });
        if (NPC != null) {
            if (!Methods.isOnScreen(NPC) && Methods.turnTo(NPC, 20)) {
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