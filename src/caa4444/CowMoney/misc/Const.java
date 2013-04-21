package caa4444.cowmoney.misc;

import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public final class Const {
    public static Timer TIMER = new Timer(0);
    public static Area TANNER_AREA = new Area(new Tile(2889, 3499, 0), new Tile(2885, 3503, 0));
    public static Area BANK_AREA = new Area(new Tile(2891, 3531, 0), new Tile(2895, 3528, 0));
    public static Area COW_AREA = new Area(new Tile(2878, 3479, 0), new Tile(2893, 3496, 0));
    public static Area COW_WALK_AREA = new Area(new Tile(2885, 3492, 0), new Tile(2887, 3488, 0));
    public static int ID_TANNER = 14877;
    public static Tile BANK_T = new Tile(2893, 3530, 0);
}