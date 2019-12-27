package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.Entity.tile.Tile;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.AssetManager;
import com.mygdx.pmd.utils.KeyInput;
import com.mygdx.pmd.utils.Mappers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerInputSystem extends EntitySystem {
    private Floor fFloor;
    private ImmutableArray<Entity> fEntities;

    public PlayerInputSystem(Floor floor) {
        fFloor = floor;
    }

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
            Vector2 pos = pc.getPos();

            // Movement system will remove the move component on same update loop
            // allow player to make input anyways to keep gameplay smooth
            if (mc != null && !mc.getDest().equals(pos)) {
                continue;
            }

            handleFace(entity);
            handleMove(entity);
        }
    }

    private void handleFace(Entity entity) {
        NameComponent nc = Mappers.Name.get(entity);
        AssetManager assets = AssetManager.getInstance();

        if (KeyInput.pressed(Input.Keys.SHIFT_LEFT)) {
            for (int key : getPressedDirectionKeys()) {
                Direction dir = Direction.get(key);
                entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), dir.format("idle"))));
            }
        }
    }

    private void handleMove(Entity entity) {
        PositionComponent pc = Mappers.Position.get(entity);
        NameComponent nc = Mappers.Name.get(entity);
        DirectionComponent dc = Mappers.Direction.get(entity);

        Vector2 pos = pc.getPos();
        Tile currentTile = fFloor.getTile(pos);
        AssetManager assets = AssetManager.getInstance();

        for (int key : getPressedDirectionKeys()) {
            Direction dir = Direction.get(key);
            dc.setDirection(dir);
            Tile nextTile = fFloor.getRelative(currentTile, dir);

            // Can only move if there is nobody on the next tile
            // Still let the entity face the direction pressed
            if (nextTile == null || !nextTile.isEmpty()) {
                entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), dir.format("idle"))));
                return;
            }

            currentTile.removeEntity(entity);
            nextTile.addEntity(entity);
            entity.add(new MoveComponent(dir, pos, (c) -> {
                // After moving set back to idle
                entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), dir.format("idle"))));
            }));
            entity.add(new AnimationComponent(assets.getAnimation(nc.getName(), dir.format("walk"))));
        }
    }

    private List<Integer> getPressedDirectionKeys() {
        return Stream.of(Input.Keys.DOWN, Input.Keys.UP, Input.Keys.RIGHT, Input.Keys.LEFT)
                .filter(KeyInput::pressed).collect(Collectors.toList());
    }
}
