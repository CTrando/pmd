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
import com.mygdx.pmd.utils.*;


/**
 * Created by Cameron on 12/22/2016.
 */
public class MobSpawnBehavior extends SpawnBehavior {

    public MobSpawnBehavior(DynamicEntity dEntity) {
        super(dEntity);
    }

    @Override
    public void execute() {
        if(dEntity.isTurnWaiting()) {
            dEntity.setTurnState(Turn.COMPLETE);

            if (controller.dEntities.size < Constants.NUM_MAX_ENTITY) {
                dEntity.setActionState(Action.SPAWNING);
                Tile tile = dEntity.floor.chooseUnoccupiedTile();
                int rand = PRandomInt.random(0, 10);
                switch (rand) {
                    case 0:
                        Pokemon pokemon = PokemonFactory.createPokemon(controller, PokemonName.getRandomName(), PokemonMob.class);
                        pokemon.setCurrentTile(tile);
                        pokemon.setNextTile(tile);

                        controller.toBeAdded(pokemon);
                        break;
                    default:
                        dEntity.setActionState(Action.IDLE);
                        break;
                }
            }
        }
    }
}
