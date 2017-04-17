package com.mygdx.pmd.model.components;

import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 4/16/2017.
 */
public class MoveComponent implements Component {
    private Entity entity;
    private Tile[][] tileBoard;

    private boolean isForcedMove;
    private int speed;
    
    private Tile currentTile;
    private Tile nextTile;
    private Tile facingTile;
    public Tile possibleNextTile;

    public MoveComponent(Entity entity) {
        this.entity = entity;
        tileBoard = entity.tileBoard;

        this.currentTile = tileBoard[0][0];
    }

    public void move(int dx, int dy) {
        entity.x += dx;
        entity.y += dy;
    }

    public void forceMoveToTile(Tile nextTile, Direction direction) {
        this.nextTile = nextTile;
        isForcedMove = true;
    }

    public void moveToTile(Tile nextTile, int speed) {
        if (nextTile == null || entity.equals(nextTile)) {
            return;
        }
        
        int y = entity.y;
        int x = entity.x;
                
        if (y > nextTile.y && x > nextTile.x) {
            move(-speed, -speed);
        } else if (y < nextTile.y && x > nextTile.x) {
            move(-speed, speed);
        } else if (y < nextTile.y && x < nextTile.x) {
            move(speed, speed);
        } else if (y > nextTile.y && x < nextTile.x) {
            move(speed, -speed);
        } else if (y > nextTile.y) {
            move(0, -speed);
        } else if (y < nextTile.y) {
            move(0, speed);
        } else if (x < nextTile.x) {
            move(speed, 0);
        } else if (x > nextTile.x) {
            move(-speed, 0);
        }
    }

    public void setCurrentTile(Tile nextTile) {
        this.currentTile = nextTile;

        entity.x = currentTile.x;
        entity.y = currentTile.y;
    }

    public void setNextTile(Tile tile) {
        if (tile == null) return;
        this.nextTile = tile;
    }

    public void setFacingTile(Direction d) {
        try {
            switch (d) {
                case up:
                    facingTile = tileBoard[currentTile.row + 1][currentTile.col];
                    break;
                case down:
                    facingTile = tileBoard[currentTile.row - 1][currentTile.col];
                    break;
                case right:
                    facingTile = tileBoard[currentTile.row][currentTile.col + 1];
                    break;
                case left:
                    facingTile = tileBoard[currentTile.row][currentTile.col - 1];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void setFacingTile(Tile tile) {
        facingTile = tile;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public Tile getNextTile(){
        return nextTile;
    }
    
    public Tile getFacingTile() {
        return facingTile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void addToTile(Tile nextTile) {
        nextTile.addEntity(entity);
    }

    public void removeFromCurrentTile() {
        currentTile.removeEntity(entity);
    }

    public int getSpeed() {
        return speed;
    }
}
