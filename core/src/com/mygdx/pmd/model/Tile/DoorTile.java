package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Factory.FloorFactory;

/**
 * Created by Cameron on 6/27/2016.
 */


public class DoorTile extends Tile {
    public DoorTile(int row, int col, FloorFactory floorFactory) {
        super(row, col, floorFactory, "DOOR");
        this.sprite = PMD.sprites.get("doortilesprite");
        this.isWalkable = true;
    }

    @Override
    public void playEvents(DynamicEntity dEntity) {

    }

    @Override
    public boolean isLegal() {
        return false;
    }

}
