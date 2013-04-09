package caa4444.hideCraft.misc;

import caa4444.hideCraft.Items;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;

public class Variables {
    public static int startingExperience = Skills.getExperience(Skills.CRAFTING),
            startingLevel = Skills.getLevel(Skills.CRAFTING), itemsMade = 0, xpHourly = 0;
    public static String status = "", xpGain = "",
            itemHour = "", xpHour = "", TTL = "";
    public static Items i = Items.BOOTS;
    public static boolean guiIsDone = false;
    public static Timer timer = new Timer(1000);
}
