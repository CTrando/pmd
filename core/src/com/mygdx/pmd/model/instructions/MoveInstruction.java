package com.mygdx.pmd.model.instructions;

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

    public MoveInstruction(DynamicEntity dEntity, Tile nextTile) {
        this.dEntity = dEntity;
        this.nextTile = nextTile;
    }

    @Override
    public void execute() {
        if (!dEntity.equals(nextTile)) {
            dEntity.moveToTile(nextTile, dEntity.speed);
        }

        if (dEntity.equals(nextTile)) {
            isFinished = true;
        }
    }

    @Override
    public void onInit() {
        dEntity.setActionState(Action.MOVING);

        dEntity.removeFromTile();
        dEntity.addToTile(nextTile);
        dEntity.setFacingTile(dEntity.getDirection());
    }

    @Override
    public void onFinish() {
        dEntity.setActionState(Action.IDLE);

        dEntity.setCurrentTile(nextTile);
        dEntity.getCurrentTile().playEvents(dEntity);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
