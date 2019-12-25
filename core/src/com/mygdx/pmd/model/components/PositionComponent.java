package com.mygdx.pmd.model.components;


import com.badlogic.ashley.core.Component;

/**
 * Created by Cameron on 4/17/2017.
 */

public class PositionComponent implements Component {
    private int fX;
    private int fY;

    public PositionComponent(int x, int y) {
        fX = x;
        fY = y;
    }

    public void setX(int x) {
        fX = x;
    }

    public void setY(int y) {
        fY = y;
    }

    public int getX() {
        return fX;
    }

    public int getY() {
        return fY;
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
