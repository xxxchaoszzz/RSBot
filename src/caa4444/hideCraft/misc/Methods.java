package caa4444.hidecraft.misc;

import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.bot.Context;

public class Methods {

    public static void stopScript(String s) {
        s(s);
        Context.get().getScriptHandler().shutdown();
    }

    public static void s(String s) {
        Variables.status = s;
        System.out.println(String.format("[Hard Leather Sweatshop] %s", s));
    }

    public static int getPerHour(int base) {
        return (int) (base * 3600000D / Variables.timer.getElapsed());
    }

    public static long TTL(int xpHour) {
        return xpHour != 0 ? (long) ((double) Skills.getExperienceToLevel(Skills.CRAFTING, Skills.getLevel(Skills.CRAFTING) + 1) / (double) xpHour * 3600000) : 0;
    }
}