package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Cameron on 2/17/2017.
 */
public class RenderSystem extends IteratingSystem {
    public RenderSystem() {
        super(Family.all(RenderComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ActionComponent am = Mappers.am.get(entity);
        RenderComponent rm = Mappers.rm.get(entity);
        PositionComponent pm = Mappers.pm.get(entity);
        DirectionComponent dm = Mappers.dm.get(entity);

        switch(am.getActionState()) {
            case MOVING:
                rm.animation = rm.animationMap.get(dm.direction.toString());
                //pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString()).getCurrentSprite();
                break;
            case ATTACKING:
                rm.animation = rm.animationMap.get(dm.direction.toString() + "attack");
                break;
            case IDLE:
             /*   if(pMob.animation != null)
                    pMob.animation.clear();*/
                rm.animation = rm.animationMap.get(dm.direction.toString() + "idle");
                //pMob.currentSprite = pMob.animationMap.get(pMob.direction.toString()).finalSprite;
        }

        rm.currentSprite = rm.animation.getCurrentSprite();
    }
}
