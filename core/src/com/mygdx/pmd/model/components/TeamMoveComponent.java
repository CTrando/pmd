package com.mygdx.pmd.model.components;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 5/16/2017.
 */
public class TeamMoveComponent extends MoveComponent {
    private Team team;

    public TeamMoveComponent(Team team) {
        super(team);
        this.team = team;
    }

    @Override
    public void randomizeLocation() {
        team.getEntities().first().getComponent(MoveComponent.class).randomizeLocation();
        Tile center = team.getEntities().first().getComponent(PositionComponent.class).getCurrentTile();
        Array<Entity> entities = team.getEntities();
        placeEntitiesAround(center, entities);
    }

    private void placeEntitiesAround(Tile center, Array<Entity> entities) {
        Tile[][] tileBoard = center.tileBoard;
        PositionComponent pc = center.getComponent(PositionComponent.class);
        Tile tile = tileBoard[pc.getCurrentTile().row + 1][pc.getCurrentTile().col];

        Entity entity = entities.get(1);
        if (entity.hasComponent(MoveComponent.class)) {
            MoveComponent mc = entity.getComponent(MoveComponent.class);
            mc.placeAt(tile);
        }
    }
}
