package com.mygdx.pmd.model.Factory;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.PokemonName;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;

/**
 * Created by Cameron on 11/6/2016.
 */
public class PokemonFactory {

    Controller controller;

    public PokemonFactory(Controller controller){
        this.controller = controller;
    }

    public static Pokemon createPokemon(Controller controller, PokemonName name, Class ident){
        Pokemon pokemon = null;
        if(ident == PokemonMob.class) {
            pokemon = new PokemonMob(controller, 0, 0, name);
        } else if(ident == PokemonPlayer.class) {
            pokemon = new PokemonPlayer(controller, 0,0, name);
        }
        pokemon.loadAnimations(name);
        return pokemon;
    }

}
