package com.mygdx.pmd.model.Behavior.Pokemon;

import com.mygdx.pmd.model.Behavior.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Entity.Projectile.*;

/**
 * Created by Cameron on 11/8/2016.
 */
public class AnimationBehavior extends BaseBehavior {

    public AnimationBehavior(Entity entity) {
        super(entity);
    }

    @Override
    public void execute() {
        switch (entity.getActionState()) {
            case MOVING:
                if (entity instanceof Projectile) {
                    entity.currentAnimation = entity.animationMap.get(Projectile.MOVE_CLASSIFIER);
                } else if (entity instanceof Pokemon) {
                    entity.currentAnimation = entity.animationMap.get(entity.getDirection().toString());
                }
                break;
            case ATTACKING:
                entity.currentAnimation = entity.animationMap.get(entity.getDirection().toString() + "attack");
                break;
            case COLLISION:
                entity.currentAnimation = entity.animationMap.get("death");
                break;
            case IDLE:
                entity.currentAnimation = entity.animationMap.get(entity.getDirection().toString() + "idle");
                break;
        }
        if (entity.currentAnimation == null) {
            System.out.println("UH OH");
        }

        entity.currentSprite = entity.currentAnimation.getCurrentSprite();
    }
}
