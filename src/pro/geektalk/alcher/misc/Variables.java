package pro.geektalk.alcher.misc;

import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;

public class Variables {
    public static int startingExperience = Skills.getExperience(Skills.MAGIC);
    public static int startingLevel = Skills.getLevel(Skills.MAGIC);
    public static int alchs;
    public static int slot;
    public static int coinsMade;
    public static boolean guiIsDone;
    public static String status = "";
    public static boolean[] slotsToAlch = new boolean[28];
    public static Timer timer = new Timer(0);
}
