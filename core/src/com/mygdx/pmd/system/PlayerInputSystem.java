package com.mygdx.pmd.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.pmd.enumerations.Key;
import com.mygdx.pmd.model.components.PlayerControlledComponent;
import com.mygdx.pmd.model.components.PositionComponent;
import com.mygdx.pmd.utils.KeyInput;

public class PlayerInputSystem extends EntitySystem {

    private ImmutableArray<Entity> fEntities;
    private ComponentMapper<PositionComponent> fPm = ComponentMapper.getFor(PositionComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(Family.all(PlayerControlledComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float dt) {
        for (Entity entity : fEntities) {
            PositionComponent pc = fPm.get(entity);

            if (KeyInput.pressed(Input.Keys.DOWN)) {
                pc.setY(pc.getY() - 10);
            }

            if (KeyInput.pressed(Input.Keys.UP)) {
                pc.setY(pc.getY() + 10);
            }

            if (KeyInput.pressed(Input.Keys.LEFT)) {
                pc.setX(pc.getX() - 10);
            }

            if (KeyInput.pressed(Input.Keys.RIGHT)) {
                pc.setX(pc.getX() + 10);
            }
        }
    }
}
