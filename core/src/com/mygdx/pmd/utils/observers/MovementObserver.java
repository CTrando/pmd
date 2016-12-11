package com.mygdx.pmd.utils.observers;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 12/4/2016.
 */
public class MovementObserver extends Observer {
    public MovementObserver(Entity entity) {
        super(entity);
    }

    @Override
    public void update() {
        entity.currentTile.playEvents();
    }


}
