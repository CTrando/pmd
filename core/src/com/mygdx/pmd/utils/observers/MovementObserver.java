package com.mygdx.pmd.utils.observers;


import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 12/4/2016.
 */
public class MovementObserver extends Observer {
    public MovementObserver(Entity entity) {
        super(entity);
    }

    /**
     * This class is actually used
     */
    @Override
    public void update() {
       entity.getCurrentTile().playEvents(entity);
    }
}
