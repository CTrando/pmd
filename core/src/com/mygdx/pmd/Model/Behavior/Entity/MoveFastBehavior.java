package com.mygdx.pmd.Model.Behavior.Entity;

import com.mygdx.pmd.Model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/11/2016.
 */
public class MoveFastBehavior extends MoveBehavior {
    public MoveFastBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute(){
        if(!this.equals(entity.currentTile)){
            entity.moveFast();
        }
        super.execute();
    }
}
