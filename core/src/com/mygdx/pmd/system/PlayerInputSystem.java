package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.AssetManager;
import com.mygdx.pmd.utils.KeyInput;
import com.mygdx.pmd.utils.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<Integer> inputs = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            if (KeyInput.pressed(i)) {
                inputs.add(i);
            }
        }

        // Nothing hit, nothing to be done
        if (inputs.isEmpty()) {
            return;
        }

        System.out.println(Arrays.toString(inputs.toArray()));

        for (Entity entity : fEntities) {
            PositionComponent pc = Mappers.Position.get(entity);
            NameComponent nc = Mappers.Name.get(entity);
            MoveComponent mc = Mappers.Movement.get(entity);

            // Movement system will remove the move component on same update loop
            // allow player to make input anyways to keep gameplay smooth
            if (mc != null && !mc.getDest().equals(pc.getPos())) {
                continue;
            }

            if (KeyInput.justPressed(Input.Keys.A)) {
                System.out.println("created here");
                entity.add(new SequenceComponent(
                        new MoveComponent(Direction.down, pc.getPos()),
                        new MoveComponent(pc.getPos()),
                        new AnimationComponent(AssetManager.getInstance().getAnimation(nc.getName(), "walkRight"))
                ));
                continue;
            }
            System.out.println("Adding");
            entity.add(new InputComponent(inputs));
        }
    }
}
