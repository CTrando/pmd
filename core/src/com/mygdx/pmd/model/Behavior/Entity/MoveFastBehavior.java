package com.mygdx.pmd.model.Behavior.Entity;

import com.mygdx.pmd.model.Entity.Pokemon.Pokemon;

/**
 * Created by Cameron on 11/11/2016.
 */
public class MoveFastBehavior extends MoveBehavior {
    public MoveFastBehavior(Pokemon pokemon) {
        super(pokemon);
    }

    @Override
    public void execute(){
        if(!this.equals(dEntity.nextTile)){
            dEntity.moveToTile(dEntity.nextTile, 5);
        }
        super.execute();
    }
}
