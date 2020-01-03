package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.KeyInput;

import java.util.ArrayList;
import java.util.List;

public class InputSystem extends EntitySystem {
    private KeyInput fInput;
    private ImmutableArray<Entity> fEntities;

    public InputSystem() {
        this(new KeyInput());
    }

    public InputSystem(KeyInput input) {
        super(9);
        fInput = input;
    }

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(
                Family.all(InputControlledComponent.class,
                        PositionComponent.class,
                        DirectionComponent.class,
                        NameComponent.class)
                        .exclude(InputLockComponent.class).get());
    }

    @Override
    public void update(float dt) {
        List<Integer> inputs = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            if (fInput.pressed(i)) {
                inputs.add(i);
            }
        }

        // Nothing hit, nothing to be done
        if (inputs.isEmpty()) {
            return;
        }

        for (Entity entity : fEntities) {
            entity.add(new InputComponent(inputs));
        }
    }
}
