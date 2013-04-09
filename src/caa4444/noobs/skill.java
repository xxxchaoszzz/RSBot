package caa4444.noobs;

import org.powerbot.game.api.methods.tab.Skills;

public enum skill {
    CONSTITUTION("Constitution", Skills.CONSTITUTION),
    DEFENCE("Defence", Skills.DEFENSE),
    MAGIC("Magic", Skills.MAGIC),
    ATTACK("Attack", Skills.ATTACK),
    STRENGTH("Strength", Skills.STRENGTH),
    RANGED("Ranged", Skills.RANGE);

    private final String name;
    private final int id;

    skill(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
