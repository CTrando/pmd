package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 2/22/2017.
 */
public class TurnInstruction implements Instruction {
    private Entity entity;
    private Direction direction;

    private boolean isFinished;

    public TurnInstruction(Entity entity, Direction direction){
        this.entity = entity;
        this.direction = direction;
    }

    @Override
    public void execute() {
        entity.setDirection(direction);
        isFinished = true;
    }

    @Override
    public void onInit() {
        System.out.println("I am turning!");
    }

    @Override
    public void onFinish() {
        System.out.println("I have finished turning!");
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
