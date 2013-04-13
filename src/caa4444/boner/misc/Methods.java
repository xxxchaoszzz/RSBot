package caa4444.boner.misc;

import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Locatable;
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
        System.out.println(String.format("[Mr Boner] %s", s));
    }

    public static int getPerHour(final int base, final long time) {
        return (int) (base * 3600000D / (System.currentTimeMillis() - time));
    }


    public static boolean dragMouse(int x1, int y1, int x2, int y2) {
        final org.powerbot.game.client.input.Mouse MOUSE = Context.client().getMouse();
        final Component TARGET = Context.get().getLoader().getComponent(0);
        Mouse.move(x1, y1);
        MOUSE.sendEvent(
                new MouseEvent(TARGET, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, Mouse.getX(), Mouse.getY(), 1, false, MouseEvent.BUTTON2)
        );
        Mouse.move(x2, y2);
        MOUSE.sendEvent(
                new MouseEvent(TARGET, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0, Mouse.getX(), Mouse.getY(), 1, false, MouseEvent.BUTTON2)
        );
        return Mouse.getX() == x2 && Mouse.getY() == y2 && !Mouse.isPressed();
    }

    public static boolean turnTo(Locatable locatable, int degreesDeviation) {
        final double DEGREES_PER_PIXEL_X = 0.35;
        final int DEGREES = Camera.getMobileAngle(locatable) % 360;
        int angleTo = Camera.getAngleTo(DEGREES);
        while (Math.abs(angleTo) > degreesDeviation) {
            angleTo = Camera.getAngleTo(DEGREES);
            int pixelsTo = (int) Math.abs(angleTo / DEGREES_PER_PIXEL_X)
                    + Random.nextInt(-(int) (degreesDeviation / DEGREES_PER_PIXEL_X) + 1,
                    (int) (degreesDeviation / DEGREES_PER_PIXEL_X) - 1);
            if (pixelsTo > 450) {
                pixelsTo = pixelsTo / 450 * 450;
            }
            final int startY = Random.nextInt(-25, 25) + Mouse.getY();
            if (angleTo > degreesDeviation) {//right
                final int startX = 500 - pixelsTo - Random.nextInt(0, 500 - pixelsTo - 10);
                dragMouse(startX, startY, startX + pixelsTo, startY + Random.nextInt(-5, 5));
            } else if (angleTo < -degreesDeviation) {//left
                final int startX = pixelsTo + 10 + Random.nextInt(0, 500 - pixelsTo + 10);
                dragMouse(startX, startY, startX - pixelsTo, startY + Random.nextInt(-5, 5));
            }
        }
        return Math.abs(Camera.getAngleTo(DEGREES)) <= degreesDeviation;
    }
}