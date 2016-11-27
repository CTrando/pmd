package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public class MoveBehavior extends BaseBehavior {

    public MoveBehavior(Entity entity) {
        super(entity);
    }

    @Override
    public void execute(){
        entity.setFacingTileBasedOnDirection(entity.direction);
    }
}
