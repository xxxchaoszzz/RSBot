package caa4444.ashcollector.misc;

import caa4444.ashcollector.Logs;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;

public class Variables {
    public static int startingExperience = Skills.getExperience(Skills.FIREMAKING);
    public static int startingLevel = Skills.getLevel(Skills.FIREMAKING);
    public static int itemsMade;
    public static int xpHourly;
    public static String status = "";
    public static String xpGain = "";
    public static String itemHour = "";
    public static String xpHour = "";
    public static String TTL = "";
    public static Logs logType = Logs.WILLOW;
    public static boolean guiIsDone;
    public static Timer timer = new Timer(1000);
    public static Area fireArea = new Area();
    public static Area cannotBurn = new Area();
}
