package caa4444.pm.misc;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Timer;
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

    public static void mineRock(int i) {
        switch (i) {
            case 1:
                if (SceneEntities.getAt(3175, 3368).interact("Mine", "Iron ore rock")) {
                    Methods.s("Mining Iron");
                    Task.sleep(200, 300);
                    SceneEntities.getAt(3175, 3366).hover();
                }
                break;
            case 0:
                if (SceneEntities.getAt(3175, 3366).interact("Mine", "Iron ore rock")) {
                    Methods.s("Mining Iron");
                    Task.sleep(200, 300);
                    SceneEntities.getAt(3175, 3368).hover();
                }
                break;
        }
        sleep(i);
    }

    public static void sleep(int i) {
        Timer wait;
        switch (i) {
            case 1:
                while (SceneEntities.getAt(3175, 3368).getId() == 11956 && Players.getLocal().isIdle())
                    Task.sleep(50);
                wait = new Timer(2000);
                while (SceneEntities.getAt(3175, 3368).getId() == 11956 && !Players.getLocal().isIdle() && wait.isRunning())
                    Task.sleep(50);
                break;
            case 0:
                while (SceneEntities.getAt(3175, 3366).getId() == 11955 && Players.getLocal().isIdle())
                    Task.sleep(50);
                wait = new Timer(2000);
                while (SceneEntities.getAt(3175, 3366).getId() == 11955 && !Players.getLocal().isIdle() && wait.isRunning())
                    Task.sleep(50);
                break;
        }
    }
}