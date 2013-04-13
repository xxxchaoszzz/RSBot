package caa4444.hideCraft.nodes;

import caa4444.hideCraft.misc.Const;
import caa4444.hideCraft.misc.Methods;
import caa4444.hideCraft.misc.Variables;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;

public class Loop extends LoopTask {

    @Override
    public int loop() {

        int gain = Skills.getExperience(Skills.CRAFTING)
                - Variables.startingExperience;
        Variables.xpHourly = Methods.getPerHour(gain);
        Variables.xpGain = Const.DF.format(Skills.getExperience(Skills.CRAFTING)
                - Variables.startingExperience);
        Variables.xpHour = Const.DF.format(Variables.xpHourly);
        Variables.itemHour = Const.DF.format(Methods.getPerHour(Variables.itemsMade));
        Variables.TTL = Time.format(Methods.TTL(Variables.xpHourly));
        return 200;
    }

}