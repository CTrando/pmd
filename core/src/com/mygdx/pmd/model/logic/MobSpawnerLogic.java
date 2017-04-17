package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Spawner.*;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.model.instructions.*;
import com.mygdx.pmd.model.logic.*;
import com.mygdx.pmd.utils.*;

/**
 * Created by Cameron on 4/8/2017.
 */
public class MobSpawnerLogic implements Logic {
    private Spawner spawner;
    private TurnComponent tc;

    public MobSpawnerLogic(Spawner spawner) {
        this.spawner = spawner;
        this.tc = spawner.tc;
    }

    @Override
    public void execute() {
        if (canSpawn()) {
            int rand = MathLogic.random(0, 10);
            if (rand == 9) {
                spawner.instructions.add(new SpawnPokemonInstruction(spawner));
            }
        }
        tc.setTurnState(Turn.COMPLETE);
    }

    private boolean canSpawn() {
        return tc.getTurnState() == Turn.WAITING && spawner.floor.getNumEntities() < Constants.NUM_MAX_ENTITY;
    }
}
