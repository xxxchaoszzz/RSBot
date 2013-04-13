package caa4444.noobs.nodes;

import caa4444.noobs.misc.Const;
import caa4444.noobs.misc.Methods;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;

public class Chain extends Node {

    @Override
    public boolean activate() {
        return Players.getLocal().getInteracting() != null
                && Widgets.get(137, 56).getTextColor() != 0x0000FF;
    }

    @Override
    public void execute() {
        if (!Widgets.get(640, 4).isOnScreen()) {
            Widgets.get(640, 3).click(true);
        }

        if (Widgets.get(640, Const.COOLDOWN_CHILD[1]).getTextureId() == 14521 &&
                Widgets.get(640, Const.ITEM_CHILD[1]).getTextColor() == 0xFFFFFF && NPCs.getLoaded(new Filter<NPC>() {
            public boolean accept(final NPC npc) {
                for (final String n : Const.NPCS) {
                    if (n.toLowerCase().contains(npc.getName().toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        }).length > 2) {
            Methods.s("Using Chain");
            Keyboard.sendText("2", false);
            Task.sleep(250);
        } else {
            for (int i = 3; i < 8; i++) {
                if (Widgets.get(640, Const.COOLDOWN_CHILD[i - 1]).getTextureId() == 14521 &&
                        Widgets.get(640, Const.ITEM_CHILD[i - 1]).getTextColor() == 0xFFFFFF) {
                    Keyboard.sendText(i + "", false);
                    Methods.s("Using Ability");
                    Task.sleep(250);
                    break;
                }
            }
        }
    }
}
