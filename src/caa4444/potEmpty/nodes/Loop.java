package caa4444.potEmpty.nodes;

import caa4444.potEmpty.misc.Const;
import caa4444.potEmpty.misc.Methods;
import caa4444.potEmpty.misc.Variables;
import org.powerbot.core.script.job.LoopTask;

public class Loop extends LoopTask {

    @Override
    public int loop() {

        Variables.itemHour = Const.DF.format(Methods.getPerHour(Variables.itemsEmptied));
        return 200;
    }

}