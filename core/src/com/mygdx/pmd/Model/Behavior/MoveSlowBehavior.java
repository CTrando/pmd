package com.mygdx.pmd.Model.Behavior;

import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.utils.Entity;

/**
 * Created by Cameron on 11/11/2016.
 */
public class MoveSlowBehavior extends MoveBehavior {
    public MoveSlowBehavior(Entity entity) {
        super(entity);
    }

    @Override
    public void execute(){
        if(!this.equals(entity.currentTile)){
            entity.moveSlow();
        }
        super.execute();
    }
}
