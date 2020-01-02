package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.mygdx.pmd.model.components.*;

public class MobSystem extends EntitySystem {
    private ImmutableArray<Entity> fEntities;

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(
                Family.all(
                        MobComponent.class,
                        PositionComponent.class,
                        DirectionComponent.class,
                        NameComponent.class)
                        .exclude(InputLockComponent.class)
                        .get());
    }

    @Override
    public void update(float dt) {
        for (Entity mob : fEntities) {
            mob.add(new InputComponent(Input.Keys.RIGHT));
        }
    }
}
