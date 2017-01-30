package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public abstract class MoveBehavior extends BaseBehavior {

    public DynamicEntity dEntity;
    public MoveBehavior(DynamicEntity dEntity) {
        super(dEntity);
        this.dEntity = dEntity;
    }
}
