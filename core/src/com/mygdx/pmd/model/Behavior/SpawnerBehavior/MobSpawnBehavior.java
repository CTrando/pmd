package com.mygdx.pmd.model.Behavior.SpawnerBehavior;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.PokemonName;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Factory.PokemonFactory;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PRandomInt;

import static com.mygdx.pmd.controller.Controller.tileBoard;

/**
 * Created by Cameron on 12/22/2016.
 */
public class MobSpawnBehavior extends SpawnBehavior {

    public MobSpawnBehavior(DynamicEntity dEntity) {
        super(dEntity);
    }

    @Override
    public void execute() {
        if (!canExecute()) return;

        if(controller.dEntities.size < Controller.NUM_MAX_ENTITY) {
            dEntity.setActionState(Action.SPAWNING);
            Tile tile = Controller.chooseUnoccupiedTile(tileBoard);
            int rand = PRandomInt.random(0, 10);
            switch (rand) {
                case 0:
                    Pokemon pokemon = PokemonFactory.createPokemon(controller, PokemonName.treeko, PokemonMob.class);
                    pokemon.setCurrentTile(tile);
                    pokemon.setNextTile(tile);

                    controller.toBeAdded(pokemon);
            }
        }
        dEntity.turnState = Turn.COMPLETE;
    }

    @Override
    public boolean canExecute() {
        if (dEntity.turnState == Turn.WAITING) {
            return true;
        }
        return false;
    }
}
