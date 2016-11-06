package com.mygdx.pmd.utils;

import com.mygdx.pmd.Model.TileType.Tile;

/**
 * Created by Cameron on 11/3/2016.
 */
public abstract class MotionBehavior {
    private Entity entity;
    
    public MotionBehavior(Entity entity){
        this.entity = entity;
    }

    public void goToTile(Tile nextTile, int speed) {
        if (nextTile == null)
            return;

        if (entity.equals(nextTile)) {
            return;
        }

        if (entity.y > nextTile.y && entity.x > nextTile.x) {
            entity.move(-speed, - speed);
        } else if (entity.y < nextTile.y && entity.x > nextTile.x) {
            entity.move(-speed, speed);
        } else if (entity.y < nextTile.y && entity.x < nextTile.x) {
            entity.move(speed, speed);
        } else if (entity.y > nextTile.y && entity.x < nextTile.x) {
            entity.move(speed, -speed);
        } else if (entity.y > nextTile.y) {
            entity.move(0, -speed);
        } else if (entity.y < nextTile.y) {
            entity.move(0, speed);
        } else if (entity.x < nextTile.x) {
            entity.move(speed, 0);
        } else if (entity.x > nextTile.x) {
            entity.move(-speed, 0);
        }
    }

    public void goToTileImmediately(Tile nextTile) {
        entity.x = nextTile.x;
        entity.y = nextTile.y;
    }

    public abstract void move();

    public void moveSlow() {
        this.goToTile(entity.currentTile, 1);
    }

    public void moveFast() {
        this.goToTileImmediately(entity.currentTile);
    }
}
