package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Model.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/11/2016.
 */
public class MoveFastBehavior extends MoveBehavior {
    public MoveFastBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute(){
        if(!this.equals(pokemon.currentTile)){
            pokemon.moveFast();
        }
        super.execute();
    }
}
