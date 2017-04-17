package com.mygdx.pmd.model.Entity;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.interfaces.Movable;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.model.components.*;


/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class DynamicEntity extends Entity implements Movable {
    public boolean isForcedMove;
    /**
     * The tile the entity is facing
     */
    public Tile facingTile;
    /**
     * Tile that needs to be legalized before it becomes the next tile, prerequisite of tile movement system
     */
    public Tile prevTile;

    public Tile possibleNextTile;

    public Aggression aggression;
    public int speed = 1;

    public DynamicEntity(Floor floor){
        this(floor, 0, 0);
    }

    public DynamicEntity(Floor floor, int x, int y) {
        super(floor, x, y);
    }

    public abstract boolean isLegalToMoveTo(Tile tile);

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void forceMoveToTile(Tile nextTile, Direction direction){
        mc.setNextTile(nextTile);
        dc.setDirection(direction);

        isForcedMove = true;
    }

    public void moveToTile(Tile nextTile, int speed) {
        if (nextTile == null)
            return;

        if (this.equals(nextTile)) {
            return;
        }

        if (this.y > nextTile.y && this.x > nextTile.x) {
            this.move(-speed, -speed);
        } else if (this.y < nextTile.y && this.x > nextTile.x) {
            this.move(-speed, speed);
        } else if (this.y < nextTile.y && this.x < nextTile.x) {
            this.move(speed, speed);
        } else if (this.y > nextTile.y && this.x < nextTile.x) {
            this.move(speed, -speed);
        } else if (this.y > nextTile.y) {
            this.move(0, -speed);
        } else if (this.y < nextTile.y) {
            this.move(0, speed);
        } else if (this.x < nextTile.x) {
            this.move(speed, 0);
        } else if (this.x > nextTile.x) {
            this.move(-speed, 0);
        }
    }

    /**
     * Sets the current tile to the parameter and sets the x and y to that of the next tile as well
     * @param nextTile the tile that will become the current tile
     */
    @Override
    public void setCurrentTile(Tile nextTile) {
        this.prevTile = getCurrentTile();
        super.setCurrentTile(nextTile);

        this.x = nextTile.x;
        this.y = nextTile.y;
    }

    public void randomizeLocation(){}

    public void setFacingTile(Direction d) {
        try {
            switch (dc.getDirection()) {
                case up:
                    facingTile = tileBoard[getCurrentTile().row + 1][getCurrentTile().col];
                    break;
                case down:
                    facingTile = tileBoard[getCurrentTile().row - 1][getCurrentTile().col];
                    break;
                case right:
                    facingTile = tileBoard[getCurrentTile().row][getCurrentTile().col + 1];
                    break;
                case left:
                    facingTile = tileBoard[getCurrentTile().row][getCurrentTile().col - 1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void setFacingTile(Tile tile) {
        facingTile = tile;
    }

    public boolean isAggressive(){
        return aggression == Aggression.aggressive;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
}
