package caa4444.potEmpty.nodes;

import caa4444.potEmpty.misc.Methods;
import caa4444.potEmpty.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

public class BankStuff extends Node {

    @Override
    public boolean activate() {
        return !Inventory.contains(Variables.itemID) || Widgets.get(762).validate();
    }

    @Override
    public void execute() {
        if (!Widgets.get(762).validate()) {
            Methods.s("Opening Bank");
            Bank.open();
            Variables.timer.reset();
            while (!Widgets.get(762).validate() && Variables.timer.isRunning())
                Task.sleep(50);
        } else {
            Methods.s("Banking");
            if (!Inventory.contains(Variables.itemID))
                Bank.depositInventory();
            if (Bank.getItemCount(Variables.itemID) < 1) {
                Methods.stopScript("Out of Leather");
                return;
            } else Bank.withdraw(Variables.itemID, Bank.Amount.ALL);
            Bank.close();
            Variables.timer.reset();
            while (Widgets.get(762).validate() && Variables.timer.isRunning())
                Task.sleep(50);
        }
    }
}