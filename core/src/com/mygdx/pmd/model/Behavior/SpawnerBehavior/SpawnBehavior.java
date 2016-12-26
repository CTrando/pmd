package com.mygdx.pmd.model.Behavior.SpawnerBehavior;

import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Entity;

/**
 * Created by Cameron on 12/22/2016.
 */
public abstract class SpawnBehavior extends BaseBehavior {
    public DynamicEntity dEntity;
    public SpawnBehavior(DynamicEntity dEntity) {
        super(dEntity);
        this.dEntity = dEntity;
    }

    @Override
    public boolean canExecute() {
        return false;
    }
}
