package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Factory.FloorFactory;

/**
 * Created by Cameron on 6/25/2016.
 */
public class RoomTile extends Tile {

    public RoomTile(int r, int c, FloorFactory floorFactory) {
        super(r, c, floorFactory, "ROOM");
        this.isWalkable = true;
        this.sprite = PMD.sprites.get("roomtilesprite");

    }

    public boolean isLegal(){
        return false;
    }
}
