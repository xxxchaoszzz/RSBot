package caa4444.cowmoney.misc;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Tile;

import java.util.Map;

public class Variables {
    public static int hides = -Inventory.getCount(1739);
    public static int hidesR;
    public static int bones = -Inventory.getCount(526);
    public static int bonesR;
    public static int beef = -Inventory.getCount(2132);
    public static int beefR;
    public static int profitR;
    public static boolean guiIsDone;
    public static boolean killCows;
    public static Mouse.Speed speed = Mouse.Speed.VERY_FAST;
    public static Map<Integer, Integer> prices = Methods.getPrices(1743, 526, 2132);
    public static String status = "";
    public static boolean tan;
    public static Loots[] loots;
    public static Tile cowTile = Methods.randomTile(Const.COW_WALK_AREA);
    public static Tile tanTile = Methods.randomTile(Const.TANNER_AREA);
    public static Tile bankTile = Methods.randomTile(Const.BANK_AREA);
}