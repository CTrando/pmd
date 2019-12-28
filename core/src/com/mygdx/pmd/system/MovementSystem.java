package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.pmd.model.components.MoveComponent;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.utils.Mappers;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> fEntities;

    public MovementSystem() {
        super(5);
    }

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, MoveComponent.class).get());
    }

    @Override
    public void update(float dt) {
        for (Entity entity : fEntities) {
            MoveComponent mc = Mappers.Movement.get(entity);
            PositionComponent pc = Mappers.Position.get(entity);

            if (pc.getPos().equals(mc.getDest())) {
                entity.remove(MoveComponent.class);
                return;
            }

            if (pc.getPos().x < mc.getDest().x) {
                pc.getPos().add(1, 0);
            } else if (pc.getPos().x > mc.getDest().x) {
                pc.getPos().add(-1, 0);
            } else if (pc.getPos().y < mc.getDest().y) {
                pc.getPos().add(0, 1);
            } else if (pc.getPos().y > mc.getDest().y) {
                pc.getPos().add(0, -1);
            }
        }
    }
}
