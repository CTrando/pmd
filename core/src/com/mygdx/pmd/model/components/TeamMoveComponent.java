package com.mygdx.pmd.model.components;

/**
 * Created by Cameron on 5/16/2017.
 */
/*
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
        Array<TestEntity> entities = team.getEntities();
        placeEntitiesAround(center, entities);
    }

    private void placeEntitiesAround(Tile center, Array<TestEntity> entities) {
        Tile[][] tileBoard = center.tileBoard;
        PositionComponent pc = center.getComponent(PositionComponent.class);
        Tile tile = tileBoard[pc.getCurrentTile().row + 1][pc.getCurrentTile().col];

        TestEntity entity = entities.get(1);
        if (entity.hasComponent(MoveComponent.class)) {
            MoveComponent mc = entity.getComponent(MoveComponent.class);
            mc.placeAt(tile);
        }
    }
}
*/
