package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Model.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/8/2016.
 */
public class MoveBehavior extends Behavior{

    public MoveBehavior(Pokemon pokemon){
        super(pokemon);
    }

    @Override
    public void execute() {
        if(!this.equals(pokemon.currentTile)){
            pokemon.moveSlow();
        }
    }


}
