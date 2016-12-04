package com.mygdx.pmd.utils.observers;

import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 12/4/2016.
 */
public abstract class Observer {
    public Entity entity;

    public Observer(Entity entity){
        this.entity = entity;
    }

    public abstract void update();
}
