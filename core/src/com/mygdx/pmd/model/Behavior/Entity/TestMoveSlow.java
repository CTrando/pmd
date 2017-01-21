package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Behavior.NoBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 1/20/2017.
 */
public class TestMoveSlow extends MoveBehavior {
    public TestMoveSlow(DynamicEntity dEntity) {
        super(dEntity);
    }

    @Override
    public void execute(){
        super.execute();
        if(dEntity.getActionState() != Action.MOVING) return;

        if(dEntity.nextTile == null) {
            dEntity.setActionState(Action.IDLE);
            return;
        }

        if(!dEntity.equals(dEntity.nextTile)){
            dEntity.moveToTile(dEntity.nextTile, 1);
        } else {
            //TODO fix this bottleneck
            dEntity.setActionState(Action.IDLE);
        }
    }
}
