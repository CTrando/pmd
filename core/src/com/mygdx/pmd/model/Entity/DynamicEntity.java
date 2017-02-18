package com.mygdx.pmd.model.Entity;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;


/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class DynamicEntity extends Entity {
    public boolean isForcedMove;

    /**
     * The next tile the entity will currentMove to
     */
    private Tile nextTile;
    /**
     * The tile the entity is facing
     */
    public Tile facingTile;
    /**
     * Tile that needs to be legalized before it becomes the next tile, prerequisite of tile movement system
     */
    public Tile possibleNextTile;

    public Aggression aggression;
    public int speed = 1;

    public DynamicEntity(Floor floor){
        this(floor, 0, 0);
    }

    public DynamicEntity(Floor floor, int x, int y) {
        super(floor, x, y);
        this.setFacingTile(getDirection());
    }

    public abstract boolean isLegalToMoveTo(Tile tile);

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void forceMoveToTile(Tile nextTile){
        this.setNextTile(nextTile);
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

    @Override
    public void setCurrentTile(Tile nextTile) {
        super.setCurrentTile(nextTile);
        this.x = nextTile.x;
        this.y = nextTile.y;
        //this.notifyObservers();
    }

    public Tile getNextTile() {
        return nextTile;
    }

    /**
     * Sets the tile and adds the entity to the next tile and removes it from the current tile
     */
    public void setNextTile(Tile tile) {
        if (tile == null) return;

        this.getCurrentTile().removeEntity(this);
        tile.addEntity(this);

        this.nextTile = tile;
        this.setDirection(nextTile);
    }

    public void updateCurrentTile(){
        Tile tile = Tile.getTileAt(x, y, tileBoard);
        if(this.equals(tile))
            this.setCurrentTile(tile);
    }

    public void randomizeLocation(){}

    public void setFacingTile(Direction d) {
        try {
            switch (getDirection()) {
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

    public boolean isAggressive(){
        return aggression == Aggression.aggressive;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
}
