package com.mygdx.pmd.model.Spawner;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.PokemonName;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Factory.PokemonFactory;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PRandomInt;

/**
 * Created by Cameron on 12/21/2016.
 */
public class MobSpawner extends Spawner {
    PokemonFactory pokemonFactory;

    public MobSpawner(Controller controller) {
        super(controller, -1, -1);
        pokemonFactory = new PokemonFactory(controller);
        this.isTurnBased = true;
        this.turnState = Turn.COMPLETE;
    }

    public void update(){
        if(turnState == Turn.WAITING) {
            Tile tile = Controller.chooseUnoccupiedTile(tileBoard);
            int rand = PRandomInt.random(0, 10);
            switch (rand) {
                case 0:
                    Pokemon pokemon = pokemonFactory.createPokemon(PokemonName.treeko, PokemonMob.class);
                    pokemon.setCurrentTile(tile);
                    controller.addEntity(pokemon);
            }
            turnState = Turn.COMPLETE;
        }
    }
}
