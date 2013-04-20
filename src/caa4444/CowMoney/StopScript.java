package caa4444.cowmoney;

import caa4444.cowmoney.misc.Methods;
import caa4444.cowmoney.misc.Variables;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

class StopScript extends LoopTask {

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 2500;
        }
        if (Widgets.get(640, 4).isOnScreen()) {
            Widgets.get(640, 30).click(true);
        }
        if (Players.getLocal().getHealthPercent() < 5) {
            Methods.stopScript("Low HP");
        }
        Variables.hidesR = Variables.hides + Inventory.getCount(1739);
        Variables.beefR = Variables.beef + Inventory.getCount(2132);
        Variables.bonesR = Variables.bones + Inventory.getCount(526);
        Variables.profitR = (Variables.prices.get(1743) - 3) * Variables.hidesR +
                Variables.prices.get(526) * Variables.bonesR +
                Variables.prices.get(2132) * Variables.beefR;
        return 300;
    }
}
