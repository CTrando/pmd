package com.mygdx.pmd.Model.Behavior.Entity;

import com.mygdx.pmd.Model.Behavior.BaseBehavior;
import com.mygdx.pmd.Model.Entity.Entity;

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
