package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MoveBehavior extends BaseBehavior {

    DynamicEntity dEntity;
    public MoveBehavior(DynamicEntity dEntity) {
        super(dEntity);
        this.dEntity = dEntity;
    }

    @Override
    public void execute(){
        if(!dEntity.equals(dEntity.getNextTile())){
            dEntity.moveToTile(dEntity.getNextTile(), dEntity.speed);
            dEntity.updateCurrentTile();
        }

        if(dEntity.equals(dEntity.getCurrentTile())) {
            dEntity.setActionState(Action.IDLE);
            dEntity.behaviors[2] = dEntity.noBehavior;
            dEntity.getCurrentTile().playEvents(dEntity);
        }
    }
}
