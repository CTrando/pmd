package com.mygdx.pmd.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.pmd.model.components.TurnComponent;
import com.mygdx.pmd.utils.Mappers;

public class TurnSystem extends EntitySystem {
    private Entity fTrackedEntity;
    private ImmutableArray<Entity> fEntities;

    public TurnSystem() {
        super(200);
    }

    @Override
    public void addedToEngine(Engine engine) {
        fEntities = engine.getEntitiesFor(
                Family.all(TurnComponent.class).get());
        ensureTrackedEntity();
    }

    @Override
    public void update(float dt) {
        ensureTrackedEntity();
        TurnComponent tc = Mappers.Turn.get(fTrackedEntity);

        // Turn has ended
        if (tc.turnEnded()) {
            int nextEntity = fEntities.indexOf(fTrackedEntity, true) + 1;
            if (nextEntity < fEntities.size()) {
                fTrackedEntity = fEntities.get(nextEntity);
            } else {
                fTrackedEntity = fEntities.first();
            }

            TurnComponent newTc = Mappers.Turn.get(fTrackedEntity);
            newTc.startTurn();
        }
    }

    private void ensureTrackedEntity() {
        if (fTrackedEntity == null) {
            if (fEntities.size() == 0) {
                return;
            }
            fTrackedEntity = fEntities.first();
            TurnComponent tc = Mappers.Turn.get(fTrackedEntity);
            tc.startTurn();
        }
    }
}
