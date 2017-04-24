package com.mygdx.pmd.model.logic;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 11/8/2016.
 */
public class AnimationLogic implements Logic {
    private Entity entity;
    private DirectionComponent dc;
    private ActionComponent ac;
    private RenderComponent rc;

    public AnimationLogic(Entity entity) {
        this.entity = entity;
        this.dc = entity.getComponent(DirectionComponent.class);
        this.ac = entity.getComponent(ActionComponent.class);
        this.rc = entity.getComponent(RenderComponent.class);
    }

    public void execute() {
        switch (ac.getActionState()) {
            case MOVING:
                entity.currentAnimation = entity.animationMap.get(dc.getDirection().toString());
                break;
            case ATTACKING:
                entity.currentAnimation = entity.animationMap.get(dc.getDirection().toString() + "attack");
                break;
            case COLLISION:
                entity.currentAnimation = entity.animationMap.get("death");
                break;
            case IDLE:
                entity.currentAnimation = entity.animationMap.get(dc.getDirection().toString() + "idle");
                break;
        }
        if (entity.currentAnimation == null) {
            System.out.println("UH OH");
        }

        rc.setSprite(entity.currentAnimation.getCurrentSprite());
    }
}
