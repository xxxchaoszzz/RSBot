package caa4444.hideCraft.misc;

import caa4444.hideCraft.Items;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;

public class Variables {
    public static int startingExperience = Skills.getExperience(Skills.CRAFTING);
    public static int startingLevel = Skills.getLevel(Skills.CRAFTING);
    public static int itemsMade;
    public static int xpHourly;
    public static String status = "";
    public static String xpGain = "";
    public static String itemHour = "";
    public static String xpHour = "";
    public static String TTL = "";
    public static Items i = Items.BOOTS;
    public static boolean guiIsDone;
    public static Timer timer = new Timer(1000);
}
