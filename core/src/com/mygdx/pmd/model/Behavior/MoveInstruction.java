package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 2/21/2017.
 */
public class MoveInstruction implements Instruction {
    private DynamicEntity dEntity;
    private Tile nextTile;
    private boolean isFinished;

    public MoveInstruction(DynamicEntity dEntity, Tile nextTile){
        this.dEntity = dEntity;
        this.nextTile = nextTile;
    }

    @Override
    public void execute() {
        if(!dEntity.equals(nextTile)){
            dEntity.moveToTile(nextTile, dEntity.speed);
        }

        if(dEntity.equals(nextTile)) {
            isFinished = true;
        }
    }

    @Override
    public void onInit() {
        dEntity.removeFromTile();
        dEntity.addToTile(nextTile);
        dEntity.setActionState(Action.MOVING);
    }

    @Override
    public void onFinish() {
        dEntity.setCurrentTile(nextTile);
        dEntity.setFacingTile(dEntity.getDirection());

        dEntity.setActionState(Action.IDLE);
        dEntity.getCurrentTile().playEvents(dEntity);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
