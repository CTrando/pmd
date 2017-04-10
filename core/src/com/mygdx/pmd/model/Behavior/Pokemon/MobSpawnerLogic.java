package com.mygdx.pmd.model.Behavior.Pokemon;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Behavior.SpawnerBehavior.*;
import com.mygdx.pmd.model.Spawner.*;
import com.mygdx.pmd.utils.PRandomInt;

/**
 * Created by Cameron on 4/8/2017.
 */
public class MobSpawnerLogic implements Logic {
    private Spawner spawner;

    public MobSpawnerLogic(Spawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void execute() {
        if (spawner.getTurnState() == Turn.WAITING) {
            int rand = PRandomInt.random(0, 10);
            if(rand == 9) {
                spawner.instructions.add(new SpawnPokemonInstruction(spawner));
            }
            spawner.setTurnState(Turn.COMPLETE);
        }
    }
}
