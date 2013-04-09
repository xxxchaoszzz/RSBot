package caa4444.CowMoney.misc;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.net.GeItem;
import org.powerbot.game.api.wrappers.Tile;

public class Variables {
    public static int hides = -Inventory.getCount(1739), profit = 0, hidesR = 0, profitR = 0,
            hidePrice = GeItem.lookup(1743).getPrice() - 3;
    public static String status = "";
    public static Tile cowTile = Methods.randomTile(Const.COW_WALK_AREA), tanTile = Methods.randomTile(Const.TANNER_AREA),
            bankTile = Methods.randomTile(Const.BANK_AREA);
}