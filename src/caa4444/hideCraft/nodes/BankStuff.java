package caa4444.hidecraft.nodes;

import caa4444.hidecraft.misc.Methods;
import caa4444.hidecraft.misc.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.node.Item;

import java.util.LinkedHashSet;

public class BankStuff extends Node {

    @Override
    public boolean activate() {
        return !Inventory.contains(1743) || Widgets.get(762).validate();
    }

    @Override
    public void execute() {
        if (Widgets.get(762).validate()) {
            Methods.s("Banking");
            if (Inventory.contains(Variables.i.getITEM_ID()) && Bank.deposit(Variables.i.getITEM_ID(), Bank.Amount.ALL)) {
                if (Inventory.getCount() > Inventory.getCount(1734)) {
                    LinkedHashSet<Integer> IDs = new LinkedHashSet<Integer>();
                    for (Item i : Inventory.getAllItems(false)) {
                        if (i.getId() != 1734 && i.getId() != 1743) {
                            IDs.add(i.getId());
                        }
                    }
                    for (Integer i : IDs) {
                        if (Bank.deposit(i, Bank.Amount.ALL)) {
                            Task.sleep(80);
                        }
                    }
                }
            }
            if (Bank.getItemCount(1743) < 1) {
                Methods.stopScript("Out of Leather");
                return;
            }
            Bank.withdraw(1743, Bank.Amount.ALL);
            Bank.close();
            Variables.timer.reset();
            while (Widgets.get(762).validate() && Variables.timer.isRunning()) {
                Task.sleep(50);
            }
        } else {
            Methods.s("Opening Bank");
            Bank.open();
            Variables.timer.reset();
            while (!Widgets.get(762).validate() && Variables.timer.isRunning()) {
                Task.sleep(50);
            }
        }
    }
}