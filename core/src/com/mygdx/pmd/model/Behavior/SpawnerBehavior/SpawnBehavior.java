package com.mygdx.pmd.model.Behavior.SpawnerBehavior;

import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Spawner.*;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/22/2016.
 */
public abstract class SpawnBehavior {
    public Tile[][] tileBoard;
    protected Spawner spawner;
    protected Floor floor;

    public SpawnBehavior(Spawner spawner) {
        this.spawner = spawner;
        this.floor = floor;
        this.tileBoard = spawner.tileBoard;
    }
}
