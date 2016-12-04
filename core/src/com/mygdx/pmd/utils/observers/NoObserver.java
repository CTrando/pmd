package com.mygdx.pmd.utils.observers;

import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 12/4/2016.
 */
public class NoObserver extends Observer {
    public NoObserver(Entity entity) {
        super(entity);
    }

    @Override
    public void update() {

    }

}
