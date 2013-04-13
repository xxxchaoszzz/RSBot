package caa4444.CowMoney.misc;

import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.bot.Context;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Methods {

    public static void stopScript(String s) {
        s(s);
        Context.get().getScriptHandler().shutdown();
    }

    public static void s(String s) {
        Variables.status = s;
        System.out.println("[Slaughterhouse] " + s);
    }

    public static int getPerHour(int i) {
        return (int) (i * 3600000D / Const.TIMER.getElapsed());
    }

    public static boolean dragMouse(int x1, int y1, int x2, int y2) {
        org.powerbot.game.client.input.Mouse MOUSE = Context.client().getMouse();
        Component TARGET = Context.get().getLoader().getComponent(0);
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
        double DEGREES_PER_PIXEL_X = 0.35;
        int degrees = Camera.getMobileAngle(locatable) % 360;
        int angleTo = Camera.getAngleTo(degrees);
        while (Math.abs(angleTo) > degreesDeviation) {
            angleTo = Camera.getAngleTo(degrees);
            int pixelsTo = (int) Math.abs(angleTo / DEGREES_PER_PIXEL_X)
                    + Random.nextInt(-(int) (degreesDeviation / DEGREES_PER_PIXEL_X) + 1,
                    (int) (degreesDeviation / DEGREES_PER_PIXEL_X) - 1);
            if (pixelsTo > 450) {
                pixelsTo = pixelsTo / 450 * 450;
            }
            int startY = Mouse.getY() < 255 && Mouse.getY() > 55 ? Random.nextInt(-25, 25) + Mouse.getY() : Random.nextInt(70, 240);
            if (angleTo > degreesDeviation) {//right
                int startX = 500 - pixelsTo - Random.nextInt(0, 500 - pixelsTo - 10);
                dragMouse(startX, startY, startX + pixelsTo, startY + Random.nextInt(90, 121));
            } else if (angleTo < -degreesDeviation) {//left
                int startX = pixelsTo + 10 + Random.nextInt(0, 500 - pixelsTo + 10);
                dragMouse(startX, startY, startX - pixelsTo, startY + Random.nextInt(90, 121));
            }
        }
        return Math.abs(Camera.getAngleTo(degrees)) <= degreesDeviation;
    }

    public static Tile randomTile(Area a) {
        return a.getTileArray()[Random.nextInt(0, a.getTileArray().length)];
    }
}