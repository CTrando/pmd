package com.mygdx.pmd.Model.Behavior.Entity;

import com.mygdx.pmd.Model.Entity.Entity;

/**
 * Created by Cameron on 11/11/2016.
 */
public class MoveSlowBehavior extends MoveBehavior {
    public MoveSlowBehavior(Entity entity) {
        super(entity);
    }

    @Override
    public void execute(){
        if(!this.equals(entity.currentTile)){
            entity.moveSlow();
        }
        super.execute();
    }
}
