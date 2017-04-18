package com.mygdx.pmd.model.components;

import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 4/17/2017.
 */
public class PositionComponent {

    public int x;
    public int y;
    private Tile currentTile;

    private Entity entity;

    public PositionComponent(Entity entity){
        this.entity = entity;
    }

    public PositionComponent(Entity entity, int x, int y){
        this.entity = entity;
        this.currentTile = entity.tileBoard[0][0];
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
