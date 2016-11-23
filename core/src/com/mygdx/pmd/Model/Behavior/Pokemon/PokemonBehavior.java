package com.mygdx.pmd.Model.Behavior.Pokemon;

import com.mygdx.pmd.Model.Behavior.BaseBehavior;
import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/17/2016.
 */
public abstract class PokemonBehavior extends BaseBehavior {
    public Pokemon pokemon;
    public PokemonBehavior(Pokemon pokemon) {
        super(pokemon);
        this.pokemon = pokemon;
    }
}
