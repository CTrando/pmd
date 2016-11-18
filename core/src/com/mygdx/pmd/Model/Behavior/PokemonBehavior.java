package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.utils.Entity;

/**
 * Created by Cameron on 11/17/2016.
 */
public abstract class PokemonBehavior extends Behavior {
    public Pokemon pokemon;
    public PokemonBehavior(Pokemon pokemon) {
        super(pokemon);
        this.pokemon = pokemon;
    }
}
