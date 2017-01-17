package com.mygdx.pmd.utils.observers;

import com.mygdx.pmd.model.Entity.DynamicEntity;


/**
 * Created by Cameron on 12/4/2016.
 */
public class MovementObserver extends Observer {
    public DynamicEntity dEntity;
    public MovementObserver(DynamicEntity dEntity) {
        super(dEntity);
        this.dEntity = dEntity;
    }

    @Override
    public void update() {
        entity.currentTile.playEvents(dEntity);
    }
}
