package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.utils.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public class MoveBehavior extends Behavior {

    public MoveBehavior(Entity entity) {
        super(entity);
    }

    @Override
    public void execute(){
        entity.setFacingTileBasedOnDirection(entity.direction);
    }
}
