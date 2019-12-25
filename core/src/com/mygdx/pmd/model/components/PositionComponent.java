package com.mygdx.pmd.model.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Cameron on 4/17/2017.
 */

public class PositionComponent implements Component {
    private Vector2 fPos;

    public PositionComponent(Vector2 pos) {
        fPos = pos;
    }

    public PositionComponent(int x, int y) {
        fPos = new Vector2(x, y);
    }

    public Vector2 getPos() {
        return fPos;
    }
}

/*
public class PositionComponent {

    public int x;
    public int y;
    private Tile currentTile;

    public PositionComponent(TestEntity entity){
        this.entity = entity;
    }

    public PositionComponent(TestEntity entity, int x, int y){
        this.entity = entity;
        this.currentTile = entity.tileBoard[y/ Constants.TILE_SIZE][x/ Constants.TILE_SIZE];
        this.x = x;
        this.y = y;
    }

    public void setCurrentTile(Tile nextTile) {
        this.currentTile = nextTile;

        this.x = currentTile.x;
        this.y = currentTile.y;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void removeFromCurrentTile() {
        currentTile.removeEntity(entity);
    }
}
*/
