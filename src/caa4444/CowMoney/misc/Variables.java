package caa4444.CowMoney.misc;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.net.GeItem;
import org.powerbot.game.api.wrappers.Tile;

public class Variables {
    public static int hides = -Inventory.getCount(1739);
    public static int profit;
    public static int hidesR;
    public static int profitR;
    public static int hidePrice = GeItem.lookup(1743).getPrice() - 3;
    public static String status = "";
    public static Tile cowTile = Methods.randomTile(Const.COW_WALK_AREA);
    public static Tile tanTile = Methods.randomTile(Const.TANNER_AREA);
    public static Tile bankTile = Methods.randomTile(Const.BANK_AREA);
}