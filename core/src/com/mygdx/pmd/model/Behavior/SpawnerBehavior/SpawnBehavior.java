package com.mygdx.pmd.model.Behavior.SpawnerBehavior;

import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/22/2016.
 */
public abstract class SpawnBehavior extends BaseBehavior {
    public DynamicEntity dEntity;
    public Tile[][] tileBoard;

    public SpawnBehavior(DynamicEntity dEntity) {
        super(dEntity);
        this.dEntity = dEntity;
        this.tileBoard = dEntity.tileBoard;
    }
}
