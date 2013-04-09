package caa4444.noobs.misc;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Tile;

public class Variables {
    public static int
            startingDExperience = Skills.getExperience(Skills.DEFENSE),
            startingDLevel = Skills.getLevel(Skills.DEFENSE),
            startingMExperience = Skills.getExperience(Skills.MAGIC),
            startingMLevel = Skills.getLevel(Skills.MAGIC),
            startingCExperience = Skills.getExperience(Skills.CONSTITUTION),
            startingCLevel = Skills.getLevel(Skills.CONSTITUTION),
            startingAExperience = Skills.getExperience(Skills.ATTACK),
            startingALevel = Skills.getLevel(Skills.ATTACK),
            startingSExperience = Skills.getExperience(Skills.STRENGTH),
            startingSLevel = Skills.getLevel(Skills.STRENGTH),
            startingRExperience = Skills.getExperience(Skills.RANGE),
            startingRLevel = Skills.getLevel(Skills.RANGE);
    public static String status = "";
    public static Tile
            startLoc = Game.getClientState() == Game.INDEX_MAP_LOADED ? Players.getLocal().getLocation() : new Tile(2885, 3486, 0);
}