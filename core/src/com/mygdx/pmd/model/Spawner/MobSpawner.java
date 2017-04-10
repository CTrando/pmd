package com.mygdx.pmd.model.Spawner;

import com.mygdx.pmd.model.Behavior.Pokemon.*;
import com.mygdx.pmd.model.Floor.*;

/**
 * Created by Cameron on 12/22/2016.
 */
public class MobSpawner extends Spawner {
    public MobSpawner(Floor floor) {
        super(floor);
        logic = new MobSpawnerLogic(this);
    }
}
