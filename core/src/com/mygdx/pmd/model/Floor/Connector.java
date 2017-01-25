package com.mygdx.pmd.model.Floor;

import com.mygdx.pmd.enumerations.ConnectFrom;
import com.mygdx.pmd.enumerations.Direction;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/15/2016.
 */
public class Connector {
    public Direction direction;
    public Tile tile;
    public ConnectFrom connectFrom;

    public Connector(Tile tile, Direction direction, ConnectFrom connectFrom){
        this.direction = direction;
        this.tile = tile;
        this.connectFrom = connectFrom;
    }
}
