package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.model.components.MoveComponent;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.utils.Mappers;

import static com.mygdx.pmd.system.RenderSystem.PPM;

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

            Vector2 x = new Vector2(1 / PPM, 0);
            Vector2 y = new Vector2(0 , 1 / PPM);

            if (pc.getPos().x < mc.getDest().x) {
                pc.getPos().add(x);
            } else if (pc.getPos().x > mc.getDest().x) {
                pc.getPos().sub(x);
            } else if (pc.getPos().y < mc.getDest().y) {
                pc.getPos().add(y);
            } else if (pc.getPos().y > mc.getDest().y) {
                pc.getPos().sub(y);
            }
        }
    }
}
