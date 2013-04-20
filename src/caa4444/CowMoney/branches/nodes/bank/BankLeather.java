package caa4444.cowmoney.branches.nodes.bank;

import caa4444.cowmoney.misc.Const;
import caa4444.cowmoney.misc.Methods;
import caa4444.cowmoney.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Timer;

public class BankLeather extends Node {
    @Override
    public boolean activate() {
        return Const.BANK_T.distance(Players.getLocal().getLocation()) < 4;
    }

    @Override
    public void execute() {
        if (!Widgets.get(762).validate()) {
            Methods.s("Opening Bank");
            Bank.open();
            final Timer timer = new Timer(1500);
            while (!Widgets.get(762).validate() && timer.isRunning()) {
                Task.sleep(50);
            }
        }
        if (Widgets.get(762).validate()) {
            Methods.s("Depositing Leather");
            Variables.bones += Inventory.getCount(526);
            Variables.beef += Inventory.getCount(2132);
            if (Bank.depositInventory()) {
                Variables.cowTile = Methods.randomTile(Const.COW_WALK_AREA);
                Variables.bankTile = Methods.randomTile(Const.BANK_AREA);
                Variables.tanTile = Methods.randomTile(Const.TANNER_AREA);
            }
        }
    }
}
