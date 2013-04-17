package caa4444.ashcollector.misc;

import org.powerbot.game.api.util.Timer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class Const {
    public static NumberFormat DF = DecimalFormat.getInstance();
    public static Timer TIMER = new Timer(3000);
    public static int[] FIRE_IDS = {70755, 70757, 70758, 70761, 70764, 70765};
}