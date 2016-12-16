package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.FloorComponent.Room;

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
    public void playEvents() {

    }

    @Override
    public boolean isLegal() {
        return false;
    }

}
