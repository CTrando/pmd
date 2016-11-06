package com.mygdx.pmd.Model.Factory;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.PokemonName;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Pokemon.PokemonMob;

/**
 * Created by Cameron on 11/6/2016.
 */
public class PokemonFactory {

    Controller controller;

    public PokemonFactory(Controller controller){
        this.controller = controller;
    }

    public Pokemon createPokemon(PokemonName name){
        Pokemon pokemon = new PokemonMob(controller, 0,0);
        pokemon.loadAnimations(name);
        return pokemon;
    }

}
