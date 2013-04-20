package caa4444.noobs.misc;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;

public class Variables {
    public static final int startingDExperience = Skills.getExperience(Skills.DEFENSE);
    public static final int startingDLevel = Skills.getLevel(Skills.DEFENSE);
    public static final int startingMExperience = Skills.getExperience(Skills.MAGIC);
    public static final int startingMLevel = Skills.getLevel(Skills.MAGIC);
    public static final int startingCExperience = Skills.getExperience(Skills.CONSTITUTION);
    public static final int startingCLevel = Skills.getLevel(Skills.CONSTITUTION);
    public static final int startingAExperience = Skills.getExperience(Skills.ATTACK);
    public static final int startingALevel = Skills.getLevel(Skills.ATTACK);
    public static final int startingSExperience = Skills.getExperience(Skills.STRENGTH);
    public static final int startingSLevel = Skills.getLevel(Skills.STRENGTH);
    public static final int startingRExperience = Skills.getExperience(Skills.RANGE);
    public static final int startingRLevel = Skills.getLevel(Skills.RANGE);
    public static String[] npcs = {"Cow", "Rat", "Chicken", "Spider", "Duck", "Drake"};
    public static String status = "";
    public static boolean guiIsDone;
    public static Timer timer = new Timer(0);
    public static final Tile
            startLoc = Game.getClientState() == Game.INDEX_MAP_LOADED ? Players.getLocal().getLocation() : new Tile(2885, 3486, 0);
}