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

    private PositionComponent pc;
    
    private Tile nextTile;
    private Tile facingTile;
    public Tile possibleNextTile;

    public MoveComponent(Entity entity) {
        this.entity = entity;
        this.pc = entity.pc;
        tileBoard = entity.tileBoard;
    }

    public void move(int dx, int dy) {
        entity.pc.x += dx;
        entity.pc.y += dy;
    }

    public void forceMoveToTile(Tile nextTile) {
        this.nextTile = nextTile;
        isForcedMove = true;
    }

    public void moveToTile(Tile nextTile, int speed) {
        if (nextTile == null || entity.equals(nextTile)) {
            return;
        }
        
        int y = entity.pc.y;
        int x = entity.pc.x;
                
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

    public void setNextTile(Tile tile) {
        if (tile == null) return;
        this.nextTile = tile;
    }

    public void setFacingTile(Direction d) {
        try {
            Tile currentTile = pc.getCurrentTile();
            int curRow = currentTile.row;
            int curCol = currentTile.col;

            switch (d) {
                case up:
                    facingTile = tileBoard[curRow + 1][curCol];
                    break;
                case down:
                    facingTile = tileBoard[curRow - 1][curCol];
                    break;
                case right:
                    facingTile = tileBoard[curRow][curCol + 1];
                    break;
                case left:
                    facingTile = tileBoard[curRow][curCol - 1];
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

    public void addToTile(Tile nextTile) {
        nextTile.addEntity(entity);
    }

    public int getSpeed() {
        return speed;
    }
}
