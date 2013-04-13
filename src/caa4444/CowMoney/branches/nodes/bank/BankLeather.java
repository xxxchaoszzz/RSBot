package caa4444.CowMoney.branches.nodes.bank;

import caa4444.CowMoney.misc.Const;
import caa4444.CowMoney.misc.Methods;
import caa4444.CowMoney.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
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
            Timer t = new Timer(1000);
            while (!Widgets.get(762).validate() && t.isRunning()) {
                Task.sleep(50);
            }
        }
        if (Widgets.get(762).validate()) {
            Methods.s("Depositing Leather");
            Bank.depositInventory();
            Variables.cowTile = Methods.randomTile(Const.COW_WALK_AREA);
            Variables.bankTile = Methods.randomTile(Const.BANK_AREA);
            Variables.tanTile = Methods.randomTile(Const.TANNER_AREA);
        }
    }
}
