package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 11/8/2016.
 */
public abstract class MoveBehavior extends BaseBehavior {
    public int speed = 1;

    public DynamicEntity dEntity;
    public MoveBehavior(DynamicEntity dEntity) {
        super(dEntity);
        this.dEntity = dEntity;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

}
