/*
package com.mygdx.pmd.model.Behavior.SpawnerBehavior;

import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.PokemonName;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Factory.PokemonFactory;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;


*/
/**
 * Created by Cameron on 12/22/2016.
 *//*

public class MobSpawnBehavior extends SpawnComponent {

    public MobSpawnBehavior(Entity entity) {
        super(entity);
    }

    @Override
    public void update() {
        if(entity.isTurnWaiting()) {
            entity.setTurnState(Turn.COMPLETE);

            if (floor.entities().size() < Constants.NUM_MAX_ENTITY) {
                entity.setActionState(Action.SPAWNING);
                Tile tile = entity.floor.chooseUnoccupiedTile();
                int rand = PRandomInt.random(0, 10);
                switch (rand) {
                    case 0:
                        Pokemon pokemon = PokemonFactory.createPokemon(floor, PokemonName.getRandomName(), PokemonMob.class);
                        pokemon.setCurrentTile(tile);
                        pokemon.setNextTile(tile);

                        floor.addEntity(pokemon);
                        break;
                    default:
                        entity.setActionState(Action.IDLE);
                        break;
                }
            }
        }
    }
}
*/
