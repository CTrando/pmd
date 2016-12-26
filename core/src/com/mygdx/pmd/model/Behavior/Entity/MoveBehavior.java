package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public class MoveBehavior extends BaseBehavior {
    public DynamicEntity dEntity;
    public MoveBehavior(DynamicEntity dEntity) {
        super(dEntity);
        this.dEntity = dEntity;
    }

    @Override
    public void execute(){
        if(!this.canExecute()) return;

        dEntity.setFacingTileBasedOnDirection(dEntity.direction);
    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
