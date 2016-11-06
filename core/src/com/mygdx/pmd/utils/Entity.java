package com.mygdx.pmd.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.Controller.Controller;
import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.Pokemon.Pokemon;
import com.mygdx.pmd.Model.TileType.StairTile;
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
    public MotionBehavior motionBehavior;

    public Controller controller;

    public Entity(Controller controller) {
        this.controller = controller;
    }

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

    public void randomizeLocation() {
        int rand = (int) (Math.random() * controller.currentFloor.getRoomTileList().size());

        Tile random = controller.currentFloor.getRoomTileList().get(rand);

        if (!(random instanceof StairTile) && random.getEntityList().size() == 0) {
            this.setNextTile(null);
            this.setCurrentTile(random);

            this.x = random.x;
            this.y = random.y;
        }
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
        return y > tile.y;
    }

    public boolean isBelow(Tile tile) {
        return y < tile.y;
    }
}
