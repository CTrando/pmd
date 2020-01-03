package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.pmd.model.components.Component;
import com.mygdx.pmd.model.components.SequenceComponent;
import com.mygdx.pmd.utils.Mappers;

public class SequenceSystem extends EntitySystem {
    private ImmutableArray<Entity> fEntities;

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(Family.all(SequenceComponent.class).get());
    }

    @Override
    public void update(float dt) {
        for (com.badlogic.ashley.core.Entity entity : fEntities) {
            SequenceComponent sc = Mappers.Sequence.get(entity);

            Component c = sc.getCurrentComponent();
            // Add the first component to get it started
            if (!sc.hasStarted()) {
                sc.start();
                entity.add(c);
                continue;
            }

            // Component has not finished, let it process
            if (!c.isRemoved()) {
                continue;
            }

            // Try to move to next component, if cannot, then we have finished
            if(sc.nextComponent()) {
                entity.add(sc.getCurrentComponent());
            } else {
                entity.remove(SequenceComponent.class);
            }
        }
    }
}
