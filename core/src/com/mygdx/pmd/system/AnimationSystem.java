package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.pmd.model.components.AnimationComponent;
import com.mygdx.pmd.model.components.RenderComponent;
import com.mygdx.pmd.utils.Mappers;

public class AnimationSystem extends EntitySystem {

    private ImmutableArray<Entity> fEntities;

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(Family.one(AnimationComponent.class).get());
    }

    @Override
    public void update(float dt) {
        for (Entity entity : fEntities) {
            AnimationComponent ac = Mappers.Animation.get(entity);
            Animation<Sprite> animation = ac.getAnimation();

            ac.addStateTime(dt);
            float stateTime = ac.getStateTime();
            Sprite curSprite = animation.getKeyFrame(stateTime, true);

            RenderComponent rc = Mappers.Render.get(entity);
            if (rc == null) {
                rc = new RenderComponent(curSprite);
                entity.add(rc);
            } else {
                rc.setSprite(animation.getKeyFrame(stateTime, true));
            }
        }
    }
}
