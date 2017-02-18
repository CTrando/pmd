package com.mygdx.pmd.model.Behavior.SpawnerBehavior;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.PokemonName;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Factory.PokemonFactory;
import com.mygdx.pmd.model.Spawner.*;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.*;


/**
 * Created by Cameron on 12/22/2016.
 */
public class MobSpawnBehavior extends SpawnBehavior {

    public MobSpawnBehavior(Spawner spawner) {
        super(spawner);
    }

    @Override
    public void execute() {
        if(spawner.getTurnState() == Turn.WAITING) {
            spawner.setTurnState(Turn.COMPLETE);

            if (floor.getDynamicEntities().size < Constants.NUM_MAX_ENTITY) {
                spawner.setActionState(Action.SPAWNING);
                Tile tile = spawner.floor.chooseUnoccupiedTile();
                int rand = PRandomInt.random(0, 10);
                switch (rand) {
                    case 0:
                        Pokemon pokemon = PokemonFactory.createPokemon(floor, PokemonName.getRandomName(), PokemonMob.class);
                        pokemon.setCurrentTile(tile);
                        pokemon.setNextTile(tile);

                        floor.addEntity(pokemon);
                        break;
                    default:
                        spawner.setActionState(Action.IDLE);
                        break;
                }
            }
        }
    }
}
