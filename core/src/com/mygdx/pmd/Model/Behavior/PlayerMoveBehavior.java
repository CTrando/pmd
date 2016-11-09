package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Enumerations.Action;
import com.mygdx.pmd.Model.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/8/2016.
 */
public class PlayerMoveBehavior extends MoveBehavior {

    public PlayerMoveBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute(){
        super.execute();
        if(pokemon.actionState == Action.MOVING && pokemon.equals(pokemon.currentTile))
            pokemon.currentTile.playEvents();
    }
}
