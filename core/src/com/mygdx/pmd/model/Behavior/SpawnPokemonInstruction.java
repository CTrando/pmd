package com.mygdx.pmd.model.Behavior;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Spawner.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.utils.PRandomInt;

/**
 * Created by Cameron on 4/8/2017.
 */
public class SpawnPokemonInstruction implements Instruction {

    private boolean isFinished;
    private Spawner spawner;

    public SpawnPokemonInstruction(Spawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void execute() {
        spawner.setActionState(Action.SPAWNING);
        Tile tile = spawner.floor.chooseUnoccupiedTile();

        Pokemon pokemon = PokemonFactory.createPokemon(spawner.floor, PokemonName.getRandomName(), PokemonMob.class);
        pokemon.setCurrentTile(tile);
        pokemon.setNextTile(tile);
        pokemon.removeFromTile();
        pokemon.addToTile(tile);

        spawner.floor.addEntity(pokemon);

        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
