package pro.geektalk.alcher.misc;

import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.bot.Context;

public class Methods {

    public static boolean isSlotOccupied(int i) {
        return Inventory.getItemAt(i) != null && Inventory.getItemAt(i).getId() != Const.NATURE_RUNE;
    }

    public static long TTL(int skill) {
        int gain = Skills.getExperience(Skills.MAGIC) - Variables.startingExperience;
        return getPerHour(gain) != 0 ? (long) ((double) Skills.getExperienceToLevel(skill, Skills.getLevel(skill) + 1) / (double) getPerHour(gain) * 3600000) : 0;
    }

    public static boolean outOfItems() {
        for (int i = 0; i < 28; i++) {
            if (Variables.slotsToAlch[i] && isSlotOccupied(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean inventoryContains(int id) {
        if (Tabs.getCurrent() != Tabs.INVENTORY) {
            Tabs.INVENTORY.open();
        }
        return isItemVisible(Inventory.getItem(id))
                && Inventory.getCount(id) > 0;
    }

    public static void stopScript(String s) {
        s(s);
        Context.get().getScriptHandler().shutdown();
    }

    public static void s(String s) {
        Variables.status = s;
        System.out.println(String.format("[EZ Alch] %s", s));
    }

    public static int getPerHour(int base) {
        return (int) (base * 3600000D / Variables.timer.getElapsed());
    }

    private static boolean isItemVisible(Item i) {
        return i != null && i.getWidgetChild().validate()
                && i.getWidgetChild().visible()
                && i.getWidgetChild().isOnScreen();
    }
}
