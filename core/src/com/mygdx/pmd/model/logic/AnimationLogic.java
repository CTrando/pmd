package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.model.Entity.*;

/**
 * Created by Cameron on 11/8/2016.
 */
public class AnimationLogic implements Logic {
    private Entity entity;

    public AnimationLogic(Entity entity) {
        this.entity = entity;
    }

    public void execute() {
        switch (entity.getActionState()) {
            case MOVING:
                entity.currentAnimation = entity.animationMap.get(entity.getDirection().toString());
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
