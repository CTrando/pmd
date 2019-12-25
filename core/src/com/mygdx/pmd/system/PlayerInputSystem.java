package com.mygdx.pmd.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.components.MoveComponent;
import com.mygdx.pmd.model.components.PlayerControlledComponent;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.utils.KeyInput;
import com.mygdx.pmd.utils.Mappers;

public class PlayerInputSystem extends EntitySystem {
    private ImmutableArray<Entity> fEntities;

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(Family.all(PlayerControlledComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float dt) {
        for (Entity entity : fEntities) {
            MoveComponent mc = Mappers.Movement.get(entity);
            PositionComponent pc = Mappers.Position.get(entity);
            Vector2 pos = pc.getPos();

            // Movement system will remove the move component on same update loop
            // allow player to make input anyways to keep gameplay smooth
            if(mc != null && !mc.getDest().equals(pos)) {
                continue;
            }

            if (KeyInput.pressed(Input.Keys.DOWN)) {
                entity.add(new MoveComponent(Direction.down, pos));
            } else

            if (KeyInput.pressed(Input.Keys.UP)) {
                entity.add(new MoveComponent(Direction.up, pos));
            } else

            if (KeyInput.pressed(Input.Keys.LEFT)) {
                entity.add(new MoveComponent(Direction.left, pos));
            } else

            if (KeyInput.pressed(Input.Keys.RIGHT)) {
                entity.add(new MoveComponent(Direction.right, pos));
            }
        }
    }
}
