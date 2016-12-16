package com.mygdx.pmd.model.FloorComponent;

import com.mygdx.pmd.enumerations.Direction;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/15/2016.
 */
public class Connector {
    public Direction direction;
    public Tile tile;

    public Connector(Tile tile, Direction direction){
        this.direction = direction;
        this.tile = tile;
    }
}
