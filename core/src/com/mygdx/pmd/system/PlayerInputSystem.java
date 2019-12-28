package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.mygdx.pmd.enums.Direction;
import com.mygdx.pmd.model.Floor;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.utils.KeyInput;
import com.mygdx.pmd.utils.Mappers;

public class PlayerInputSystem extends PokemonInputSystem {
    private ImmutableArray<Entity> fEntities;

    public PlayerInputSystem(Floor floor) {
        super(floor, 10);
    }

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(
                Family.all(InputComponent.class,
                        PositionComponent.class,
                        DirectionComponent.class,
                        NameComponent.class)
                        .exclude(InputLockComponent.class)
                        .get());
    }

    @Override
    public void update(float dt) {
        for (Entity entity : fEntities) {
            PositionComponent pc = Mappers.Position.get(entity);
            NameComponent nc = Mappers.Name.get(entity);
            InputComponent ic = Mappers.Input.get(entity);

            if (ic.pressed(Input.Keys.A)) {
                System.out.println("created here");
                entity.add(new SequenceComponent(
                        new MoveComponent(Direction.down, pc.getPos()),
                        new MoveComponent(pc.getPos()),
                        new AnimationComponent(nc.getName(), "walkRight")
                ));
                entity.remove(InputComponent.class);
                continue;
            }

            if (ic.pressed(Input.Keys.B)) {
                System.out.println("created here");
                entity.add(new SequenceComponent(
                        new AnimationComponent(nc.getName(), "idleRight", false),
                        new AnimationComponent(nc.getName(), "idleLeft", false),
                        new AnimationComponent(nc.getName(), "idleUp", false),
                        new AnimationComponent(nc.getName(), "idleDown", false),
                        new AnimationComponent(nc.getName(), "walkRight", false)
                ));
                entity.remove(InputComponent.class);
            }

            super.update(entity);
        }
    }
}
