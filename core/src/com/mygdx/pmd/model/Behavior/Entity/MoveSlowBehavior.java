package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 11/11/2016.
 */
public class MoveSlowBehavior extends MoveBehavior {
    public MoveSlowBehavior(DynamicEntity dEntity) {
        super(dEntity);
    }

    @Override
    public void execute(){
        super.execute();
        if(dEntity.getActionState() != Action.MOVING) return;

        if(dEntity.nextTile == null) return;

        if(!dEntity.equals(dEntity.nextTile)){
            dEntity.moveToTile(dEntity.nextTile, 1);
        }
    }
}
