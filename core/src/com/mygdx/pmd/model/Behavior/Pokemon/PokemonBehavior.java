package com.mygdx.pmd.model.Behavior.Pokemon;

import com.mygdx.pmd.model.Behavior.BaseBehavior;
import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/17/2016.
 */
public abstract class PokemonBehavior extends BaseBehavior {
    public Pokemon pMob;
    public PokemonBehavior(Pokemon pokemon) {
        super(pokemon);
        this.pMob = pokemon;
    }
}
