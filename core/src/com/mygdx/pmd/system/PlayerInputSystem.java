package com.mygdx.pmd.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.AssetManager;
import com.mygdx.pmd.utils.KeyInput;
import com.mygdx.pmd.utils.Mappers;

public class PlayerInputSystem extends EntitySystem {
    private ImmutableArray<Entity> fEntities;

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(
                Family.all(PlayerControlledComponent.class,
                        PositionComponent.class,
                        DirectionComponent.class,
                        NameComponent.class).get());
    }

    @Override
    public void update(float dt) {
        for (Entity entity : fEntities) {
            MoveComponent mc = Mappers.Movement.get(entity);
            PositionComponent pc = Mappers.Position.get(entity);
            NameComponent nc = Mappers.Name.get(entity);
            DirectionComponent dc = Mappers.Direction.get(entity);
            Vector2 pos = pc.getPos();
            AssetManager assets = AssetManager.getInstance();


            // Movement system will remove the move component on same update loop
            // allow player to make input anyways to keep gameplay smooth
            if(mc != null && !mc.getDest().equals(pos)) {
                continue;
            }

            // If waiting on user input, set animation state to idle
            entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), dc.getDirection().format("idle"))));

            if (KeyInput.pressed(Input.Keys.DOWN)) {
                dc.setDirection(Direction.down);
                entity.add(new MoveComponent(Direction.down, pos));
                entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), Direction.down.format("walk"))));
            } else

            if (KeyInput.pressed(Input.Keys.UP)) {
                dc.setDirection(Direction.up);
                entity.add(new MoveComponent(Direction.up, pos));
                entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), Direction.up.format("walk"))));
            } else

            if (KeyInput.pressed(Input.Keys.LEFT)) {
                dc.setDirection(Direction.left);
                entity.add(new MoveComponent(Direction.left, pos));
                entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), Direction.left.format("walk"))));
            } else

            if (KeyInput.pressed(Input.Keys.RIGHT)) {
                dc.setDirection(Direction.right);
                entity.add(new MoveComponent(Direction.right, pos));
                entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), Direction.right.format("walk"))));
            }
        }
    }
}
