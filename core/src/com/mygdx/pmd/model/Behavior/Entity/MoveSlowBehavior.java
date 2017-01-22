package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Behavior.NoBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 1/20/2017.
 */
public class MoveSlowBehavior extends MoveBehavior {
    public MoveSlowBehavior(DynamicEntity dEntity) {
        super(dEntity);
    }

    @Override
    public void execute(){
        if(!dEntity.equals(dEntity.nextTile)){
            dEntity.moveToTile(dEntity.nextTile, 1);
        } else {
            dEntity.setActionState(Action.IDLE);
        }
    }
}
