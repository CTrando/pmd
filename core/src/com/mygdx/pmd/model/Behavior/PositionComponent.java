package com.mygdx.pmd.model.Behavior;

import com.badlogic.ashley.core.Component;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.utils.Constants;
import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;
import javafx.geometry.Pos;

/**
 * Created by Cameron on 2/17/2017.
 */
public class PositionComponent implements Component {

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

    public int x;
    public int y;

    public int row;
    public int col;

    public Floor floor;

    private Tile currentTile;
    public Tile[][] tileBoard;
    public int speed = 1;

    public PositionComponent(Floor floor, int x, int y){
        this.floor = floor;
        this.tileBoard = floor.tileBoard;

        this.x = x;
        this.y = y;

        this.currentTile = tileBoard[y/ Constants.TILE_SIZE][x/Constants.TILE_SIZE];
    }

    public void setCurrentTile(Tile nextTile) {
        this.currentTile = nextTile;
        this.x = nextTile.x;
        this.y = nextTile.y;
        //this.notifyObservers();
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
        if (nextTile == null) return;

        /*currentTile.removeEntity(this);
        nextTile.addEntity(this);*/

        // should be done in main logic  entity.directionComponent.setFacingTile(nextTile);
    }

    public boolean equals(Tile tile) {
        if (tile == null) return false;
        return (tile.x == x && tile.y == y);
    }

}
