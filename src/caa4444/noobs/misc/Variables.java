package caa4444.noobs.misc;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Tile;

public class Variables {
    public static int startingDExperience = Skills.getExperience(Skills.DEFENSE);
    public static int startingDLevel = Skills.getLevel(Skills.DEFENSE);
    public static int startingMExperience = Skills.getExperience(Skills.MAGIC);
    public static int startingMLevel = Skills.getLevel(Skills.MAGIC);
    public static int startingCExperience = Skills.getExperience(Skills.CONSTITUTION);
    public static int startingCLevel = Skills.getLevel(Skills.CONSTITUTION);
    public static int startingAExperience = Skills.getExperience(Skills.ATTACK);
    public static int startingALevel = Skills.getLevel(Skills.ATTACK);
    public static int startingSExperience = Skills.getExperience(Skills.STRENGTH);
    public static int startingSLevel = Skills.getLevel(Skills.STRENGTH);
    public static int startingRExperience = Skills.getExperience(Skills.RANGE);
    public static int startingRLevel = Skills.getLevel(Skills.RANGE);
    public static String status = "";
    public static Tile
            startLoc = Game.getClientState() == Game.INDEX_MAP_LOADED ? Players.getLocal().getLocation() : new Tile(2885, 3486, 0);
}