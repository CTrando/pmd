package com.mygdx.pmd.model.instructions;

/**
 * Created by Cameron on 2/21/2017.
 */
public class NoInstruction implements Instruction {

    @Override
    public void execute() {

    }

    @Override
    public void onInit() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
