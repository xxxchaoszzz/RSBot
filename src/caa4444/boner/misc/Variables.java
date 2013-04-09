package caa4444.boner.misc;

import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.node.GroundItem;

public class Variables {
    public static int startingExperience = Skills.getExperience(Skills.PRAYER),
            startingLevel = Skills.getLevel(Skills.PRAYER), bonesBuried = 0;
    public static long startTime;
    public static String status = "";
    public static GroundItem bone;

}
