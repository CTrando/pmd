package com.mygdx.pmd.model.Behavior.Pokemon;

import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;

/**
 * Created by Cameron on 11/8/2016.
 */
public class AnimationBehavior extends BaseBehavior {

    public AnimationBehavior(Entity entity){
        super(entity);
    }

    @Override
    public void execute() {
        switch(entity.getActionState()) {
            case MOVING:
                entity.currentAnimation = entity.animationMap.get(entity.getDirection().toString());
                break;
            case ATTACKING:
                entity.currentAnimation = entity.animationMap.get(entity.getDirection().toString() + "attack");
                break;
            case IDLE:
                entity.currentAnimation = entity.animationMap.get(entity.getDirection().toString() + "idle");
        }
        entity.currentSprite = entity.currentAnimation.getCurrentSprite();
    }
}
