package com.mygdx.pmd.Model.Tile;


import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.FloorComponent.Room;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 6/27/2016.
 */

public class DoorTile extends Tile {

    public Room room;

    public DoorTile(int row, int col, Floor floor, Room room) {
        super(row, col, floor, "DOOR");
        this.room = room;
        this.sprite = DungeonScreen.sprites.get("doortilesprite");
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
