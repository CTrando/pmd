package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.AssetManager;
import com.mygdx.pmd.utils.KeyInput;
import com.mygdx.pmd.utils.Mappers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            if (mc != null && !mc.getDest().equals(pos)) {
                continue;
            }

            List<Integer> pressed = Stream.of(Input.Keys.DOWN, Input.Keys.UP, Input.Keys.RIGHT, Input.Keys.LEFT)
                    .filter(KeyInput::pressed).collect(Collectors.toList());

            if (KeyInput.pressed(Input.Keys.A)) {
                entity.add(new SequenceComponent(new MoveComponent(Direction.down, pos),
                        new MoveComponent(Direction.up, pos),
                        new AnimationComponent(assets.getAnimation(nc.getName(), Direction.right.format("idle")))
                ));
                continue;
            }

            if (KeyInput.pressed(Input.Keys.SHIFT_LEFT)) {
                for (int key : pressed) {
                    Direction dir = Direction.get(key);
                    entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), dir.format("idle"))));
                }
                continue;
            }

            for (int key : pressed) {
                Direction dir = Direction.get(key);
                dc.setDirection(dir);
                entity.add(new MoveComponent(dir, pos, (c) -> {
                    // After moving set back to idle
                    entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), dir.format("idle"))));
                }));
                entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), dir.format("walk"))));
            }
        }
    }
}
