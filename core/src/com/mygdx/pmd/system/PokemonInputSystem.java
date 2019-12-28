package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.Entity.tile.Tile;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.Mappers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class PokemonInputSystem extends EntitySystem {
    private Floor fFloor;

    public PokemonInputSystem(Floor floor, int priority) {
        super(priority);
        fFloor = floor;
    }

    protected void update(Entity entity) {
        handleFace(entity);
        handleMove(entity);
    }

    private void handleFace(Entity entity) {
        NameComponent nc = Mappers.Name.get(entity);
        InputComponent ic = Mappers.Input.get(entity);

        if (ic.pressed(Input.Keys.SHIFT_LEFT)) {
            for (int key : getPressedDirectionKeys(ic)) {
                Direction dir = Direction.get(key);
                entity.add(new AnimationComponent(nc.getName(), dir.format("idle")));
            }
        }
    }

    private void handleMove(Entity entity) {
        PositionComponent pc = Mappers.Position.get(entity);
        NameComponent nc = Mappers.Name.get(entity);
        InputComponent ic = Mappers.Input.get(entity);
        DirectionComponent dc = Mappers.Direction.get(entity);

        // Don't move if pressing shift
        if (ic.pressed(Input.Keys.SHIFT_LEFT)) {
            return;
        }

        Vector2 pos = pc.getPos();
        Tile currentTile = fFloor.getTile(pos);

        for (int key : getPressedDirectionKeys(ic)) {
            Direction dir = Direction.get(key);
            dc.setDirection(dir);
            Tile nextTile = fFloor.getRelative(currentTile, dir);

            // Can only move if there is nobody on the next tile
            // Still let the entity face the direction pressed
            if (nextTile == null || !nextTile.isEmpty()) {
                entity.add(new AnimationComponent(nc.getName(), dir.format("idle")));
                return;
            }

            currentTile.removeEntity(entity);
            nextTile.addEntity(entity);
            entity.add(new MoveComponent(dir, pos, (c) -> {
                // After moving set back to idle
                entity.add(new AnimationComponent(nc.getName(), dir.format("idle")));
                entity.remove(InputLockComponent.class);
            }));
            entity.add(new AnimationComponent(nc.getName(), dir.format("walk")));
            entity.add(new InputLockComponent());
            entity.remove(InputComponent.class);

            // Only one key should be pressed at a time
            return;
        }
    }

    private List<Integer> getPressedDirectionKeys(InputComponent ic) {
        return Stream.of(Input.Keys.DOWN, Input.Keys.UP, Input.Keys.RIGHT, Input.Keys.LEFT)
                .filter(ic::pressed).collect(Collectors.toList());
    }

    protected Floor getFloor() {
        return fFloor;
    }
}
