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

    public MoveInstruction(DynamicEntity dEntity, Tile nextTile){
        this.dEntity = dEntity;
        this.nextTile = nextTile;;
    }

    @Override
    public void execute() {
        if(!dEntity.equals(nextTile)){
            dEntity.moveToTile(nextTile, dEntity.speed);
            dEntity.updateCurrentTile();
        }

        if(dEntity.equals(nextTile)) {
            dEntity.behaviors[2] = dEntity.noBehavior;
        }
    }

    @Override
    public boolean isFinished() {
        if(dEntity.equals(nextTile)){
            dEntity.getCurrentTile().playEvents(dEntity);
            return true;
        }
        return false;
    }
}
