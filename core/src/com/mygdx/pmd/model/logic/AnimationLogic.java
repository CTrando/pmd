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
    private AnimationComponent anc;

    public AnimationLogic(Entity entity) {
        this.entity = entity;
        this.dc = entity.getComponent(DirectionComponent.class);
        this.ac = entity.getComponent(ActionComponent.class);
        this.anc = entity.getComponent(AnimationComponent.class);
        this.rc = entity.getComponent(RenderComponent.class);
    }

    public void execute() {
        //TODO clear if it is different than previous state

        switch (ac.getActionState()) {
            case MOVING:
                anc.setCurrentAnimation(anc.getAnimation(dc.getDirection().toString()));
                break;
            case ATTACKING:
                anc.setCurrentAnimation(anc.getAnimation(dc.getDirection().toString() + "attack"));
                break;
            case COLLISION:
                anc.setCurrentAnimation(anc.getAnimation("death"));
                break;
            case IDLE:
                anc.setCurrentAnimation(anc.getAnimation(dc.getDirection().toString() + "idle"));
                break;
        }
        /*if (entity.currentAnimation == null) {
            System.out.println("UH OH");
        }*/

        rc.setSprite(anc.getCurrentSprite());
    }
}
