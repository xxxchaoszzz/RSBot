package pro.geektalk.alcher.misc;

import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;

public class Variables {
    public static int startingExperience = Skills.getExperience(Skills.MAGIC),
            startingLevel = Skills.getLevel(Skills.MAGIC),
            alchs = 0, slot, coinsMade = 0;
    public static boolean guiIsDone = false;
    public static String status = "";
    public static boolean[] slotsToAlch = new boolean[28];
    public static Timer timer = new Timer(0);
}
