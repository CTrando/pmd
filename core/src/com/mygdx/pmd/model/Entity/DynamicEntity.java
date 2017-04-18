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



    public void randomizeLocation(){}

    public boolean isAggressive(){
        return aggression == Aggression.aggressive;
    }

}
