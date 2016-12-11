package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 11/11/2016.
 */
public class MoveSlowBehavior extends MoveBehavior {
    public MoveSlowBehavior(Entity entity) {
        super(entity);
    }

    @Override
    public void execute(){
        if(entity.nextTile == null) return;

        if(!entity.equals(entity.nextTile)){
            entity.moveToTile(entity.nextTile, 1);
        }
        super.execute();
    }
}
