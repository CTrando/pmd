package com.mygdx.pmd.Model.Factory;

import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Enumerations.PokemonName;
import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;
import com.mygdx.pmd.Model.Entity.Pokemon.PokemonMob;
import com.mygdx.pmd.Model.Entity.Pokemon.PokemonPlayer;

/**
 * Created by Cameron on 11/6/2016.
 */
public class PokemonFactory {

    Controller controller;

    public PokemonFactory(Controller controller){
        this.controller = controller;
    }

    public Pokemon createPokemon(PokemonName name, Class ident){
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
