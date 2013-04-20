package caa4444.noobs.misc;

import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.bot.Context;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Methods {

    public static void stopScript(final String s) {
        s(s);
        Context.get().getScriptHandler().shutdown();
    }

    public static void s(final String s) {
        Variables.status = s;
        System.out.println("[Slaughterhouse] " + s);
    }

    public static int startLv(int skill) {
        switch (skill) {
            case Skills.MAGIC:
                return Variables.startingMLevel;
            case Skills.CONSTITUTION:
                return Variables.startingCLevel;
            case Skills.DEFENSE:
                return Variables.startingDLevel;
            case Skills.ATTACK:
                return Variables.startingALevel;
            case Skills.STRENGTH:
                return Variables.startingSLevel;
            case Skills.RANGE:
                return Variables.startingRLevel;
        }
        return 0;
    }

    public static int xpGained(int skill) {
        int startXP = 0;
        switch (skill) {
            case Skills.MAGIC:
                startXP = Variables.startingMExperience;
                break;
            case Skills.CONSTITUTION:
                startXP = Variables.startingCExperience;
                break;
            case Skills.DEFENSE:
                startXP = Variables.startingDExperience;
                break;
            case Skills.ATTACK:
                startXP = Variables.startingAExperience;
                break;
            case Skills.STRENGTH:
                startXP = Variables.startingSExperience;
                break;
            case Skills.RANGE:
                startXP = Variables.startingRExperience;
                break;
        }
        return Skills.getExperience(skill) - startXP;
    }

    public static int getPerHour(int skill) {
        return (int) (xpGained(skill) * 3600000D / Variables.timer.getElapsed());
    }

    public static long TTL(int skill) {
        return getPerHour(skill) != 0 ? (long) ((double) Skills.getExperienceToLevel(skill, Skills.getLevel(skill) + 1) / (double) getPerHour(skill) * 3600000) : 0;
    }

    public static boolean notOnScreen(NPC p) {
        return !new Rectangle(0, 50, 515, 260).contains(p.getCentralPoint());
    }

    private static boolean dragMouse(int x1, int y1, int x2, int y2) {
        final org.powerbot.game.client.input.Mouse mouse = Context.client().getMouse();
        final Component target = Context.get().getLoader().getComponent(0);
        Mouse.move(x1, y1);
        mouse.sendEvent(
                new MouseEvent(target, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, Mouse.getX(), Mouse.getY(), 1, false, MouseEvent.BUTTON2)
        );
        Mouse.move(x2, y2);
        mouse.sendEvent(
                new MouseEvent(target, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0, Mouse.getX(), Mouse.getY(), 1, false, MouseEvent.BUTTON2)
        );
        return Mouse.getX() == x2 && Mouse.getY() == y2 && !Mouse.isPressed();
    }

    public static boolean turnTo(Locatable locatable, int degreesDeviation) {
        final double degreesPerPixelX = 0.35;
        final int degrees = Camera.getMobileAngle(locatable) % 360;
        int angleTo = Camera.getAngleTo(degrees);
        while (Math.abs(angleTo) > degreesDeviation) {
            angleTo = Camera.getAngleTo(degrees);
            int pixelsTo = (int) Math.abs(angleTo / degreesPerPixelX)
                    + Random.nextInt(-(int) (degreesDeviation / degreesPerPixelX) + 1,
                    (int) (degreesDeviation / degreesPerPixelX) - 1);
            if (pixelsTo > 450) {
                pixelsTo = pixelsTo / 450 * 450;
            }
            final int startY = Mouse.getY() < 255 && Mouse.getY() > 55 ? Random.nextInt(-25, 25) + Mouse.getY() : Random.nextInt(70, 240);
            if (angleTo > degreesDeviation) {//right
                final int startX = 500 - pixelsTo - Random.nextInt(0, 500 - pixelsTo - 10);
                dragMouse(startX, startY, startX + pixelsTo, startY + Random.nextInt(90, 121));
            } else if (angleTo < -degreesDeviation) {//left
                final int startX = pixelsTo + 10 + Random.nextInt(0, 500 - pixelsTo + 10);
                dragMouse(startX, startY, startX - pixelsTo, startY + Random.nextInt(90, 121));
            }
        }
        return Math.abs(Camera.getAngleTo(degrees)) <= degreesDeviation;
    }
}