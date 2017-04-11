package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 4/1/2017.
 */
public class CollideInstruction implements Instruction{
    DynamicEntity dEntity;

    public CollideInstruction(DynamicEntity dEntity){
        this.dEntity = dEntity;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void execute() {
        dEntity.setActionState(Action.COLLISION);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
