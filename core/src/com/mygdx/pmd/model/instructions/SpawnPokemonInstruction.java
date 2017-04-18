package com.mygdx.pmd.model.instructions;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Spawner.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 4/8/2017.
 */
public class SpawnPokemonInstruction implements Instruction {

    private boolean isFinished;
    private Spawner spawner;
    private ActionComponent ac;

    public SpawnPokemonInstruction(Spawner spawner) {
        this.spawner = spawner;
        this.ac = spawner.ac;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void execute() {
        ac.setActionState(Action.SPAWNING);
        Tile tile = spawner.floor.chooseUnoccupiedTile();

        Pokemon pokemon = PokemonFactory.createPokemon(spawner.floor, PokemonName.getRandomName(), PokemonMob.class);
        pokemon.pc.setCurrentTile(tile);
        pokemon.mc.setNextTile(tile);

        pokemon.pc.removeFromCurrentTile();
        pokemon.mc.addToTile(tile);

        spawner.floor.addEntity(pokemon);
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
