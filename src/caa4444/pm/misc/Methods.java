package caa4444.pm.misc;

import org.powerbot.game.bot.Context;

public class Methods {

    public static void stopScript(final String s) {
        s(s);
        Context.get().getScriptHandler().shutdown();
    }

    public static void s(final String s) {
        Variables.status = s;
        System.out.println(String.format("[Pumping Iron] %s", s));
    }

    public static int getPerHour(final int base, final long time) {
        return (int) ((base) * 3600000D / (System.currentTimeMillis() - time));
    }
}