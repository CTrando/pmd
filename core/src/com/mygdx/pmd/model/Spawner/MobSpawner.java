package com.mygdx.pmd.model.Spawner;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Behavior.SpawnerBehavior.MobSpawnBehavior;

/**
 * Created by Cameron on 12/22/2016.
 */
public class MobSpawner extends Spawner {
    public MobSpawner(Controller controller) {
        super(controller, -1, -1);
        behaviors[0] = new MobSpawnBehavior(this);
    }



}
