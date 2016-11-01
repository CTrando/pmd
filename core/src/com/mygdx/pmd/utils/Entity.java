package com.mygdx.pmd.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.TileType.Tile;

import java.util.ArrayList;

/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class Entity implements Renderable, Updatable{
    public int x;
    public int y;

    public int row;
    public int col;

    public int hp =100;

    public Tile nextTile;
    public Tile currentTile;

    public Sprite currentSprite;

    @Override
    public void render(SpriteBatch batch) {
        if (currentSprite != null) {
            batch.draw(currentSprite, x, y, currentSprite.getWidth(), currentSprite.getHeight());
        }
        //I've done the previous sprite thing here before and for whatever reason it didn't work out so don't try it
    }

    public abstract void updateAnimation();

    public abstract void updateLogic();

    public abstract void updatePosition();

    public abstract boolean isLegalToMoveTo(Tile tile);

    public boolean equals(Tile tile)
    {
        return (tile.x == x && tile.y == y);
    }

    public void move(int dx, int dy) {
        x+=dx;
        y+=dy;
    }

    public void goToTile(Tile nextTile, int speed) {
        if (nextTile == null)
            return;

        if (this.equals(nextTile)) {
            return;
        }

        if (y > nextTile.y && x > nextTile.x) {
            this.move(-speed, - speed);
        } else if (y < nextTile.y && x > nextTile.x) {
            this.move(-speed, speed);
        } else if (y < nextTile.y && x < nextTile.x) {
            this.move(speed, speed);
        } else if (y > nextTile.y && x < nextTile.x) {
            this.move(speed, -speed);
        } else if (y > nextTile.y) {
            this.move(0, -speed);
        } else if (y < nextTile.y) {
            this.move(0, speed);
        } else if (x < nextTile.x) {
            this.move(speed, 0);
        } else if (x > nextTile.x) {
            this.move(-speed, 0);
        }
    }

    public void goToTileImmediately(Tile nextTile) {
        this.x = nextTile.x;
        this.y = nextTile.y;
    }

    public void moveSlow() {
        this.goToTile(currentTile, 1);
    }

    public void moveFast() {
        this.goToTileImmediately(currentTile);
    }

    public boolean isWithinArea(ArrayList<Tile> area) {
        for (Tile t : area) {
            if (t == this.currentTile) {
                return true;
            }
        }
        return false;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile nextTile) {
        if(nextTile == null) return;
        if (this.currentTile != nextTile) {
            if(currentTile != null)
                this.currentTile.removeEntity(this);
            nextTile.addEntity(this);
            this.currentTile = nextTile;
            this.row = currentTile.row;
            this.col = currentTile.col;
        }
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile tile) {
        this.nextTile = tile;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp <= 0) {
            this.hp = 0;
        }
    }

    public void takeDamage(int x) {
        this.setHp(this.getHp() - x);
    }

    public void dealDamage(Entity entity, int damage) {
        entity.takeDamage(damage);
    }

    public boolean isToRight(Tile tile) {
        return x > tile.x;
    }

    public boolean isToLeft(Tile tile) {
        return x < tile.x;
    }

    public boolean isAbove(Tile tile) {
        return y > tile.getY();
    }

    public boolean isBelow(Tile tile) {
        return y < tile.getY();
    }
}
