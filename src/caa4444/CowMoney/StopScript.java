package caa4444.CowMoney;

import caa4444.CowMoney.misc.Methods;
import caa4444.CowMoney.misc.Variables;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

public class StopScript extends LoopTask {

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED)
            return 2500;
        if (Widgets.get(640, 4).isOnScreen())
            Widgets.get(640, 30).click(true);
        if (Players.getLocal().getHealthPercent() < 5)
            Methods.stopScript("Low HP");
        Variables.hidesR = Variables.hides + Inventory.getCount(1739);
        Variables.profit = Variables.hidePrice * Variables.hides;
        Variables.profitR = Variables.hidePrice * Variables.hidesR;
        return 300;
    }
}
