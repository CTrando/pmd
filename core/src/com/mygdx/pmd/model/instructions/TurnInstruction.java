package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 2/22/2017.
 */
public class TurnInstruction implements Instruction {
    private Entity entity;
    private DirectionComponent dc;

    private boolean isFinished;

    public TurnInstruction(Entity entity, Direction direction){
        this.entity = entity;
        this.dc = (DirectionComponent) entity.getComponent(DirectionComponent.class);
    }

    @Override
    public void execute() {
        dc.setDirection(dc.getDirection());
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
