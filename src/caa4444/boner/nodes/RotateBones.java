package caa4444.boner.nodes;

import caa4444.boner.misc.Methods;
import caa4444.boner.misc.Variables;
import org.powerbot.core.script.job.state.Node;

public class RotateBones extends Node {

    @Override
    public boolean activate() {
        return Variables.bone.validate() && !Variables.bone.isOnScreen();
    }

    @Override
    public void execute() {
        Methods.s("Rotating to Bones");
        Methods.turnTo(Variables.bone, 20);
    }
}