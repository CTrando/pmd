package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.FloorComponent.Floor;
import com.mygdx.pmd.model.FloorComponent.Room;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 6/27/2016.
 */

public class DoorTile extends Tile {

    public Room room;

    public DoorTile(int row, int col, Floor floor, Room room) {
        super(row, col, floor, "DOOR");
        this.room = room;
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
