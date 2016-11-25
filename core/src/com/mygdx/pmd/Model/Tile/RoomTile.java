package com.mygdx.pmd.Model.Tile;


import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.FloorComponent.Room;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 6/25/2016.
 */
public class RoomTile extends Tile {
    public Room currentRoom;
    public RoomTile(int r, int c, Room room, Floor floor) {
        super(r, c, floor, "ROOM");
        this.isWalkable = true;
        this.sprite = DungeonScreen.sprites.get("roomtilesprite");

        this.currentRoom = room;
    }

    public boolean isLegal()
    {
        int counter = 0;
        int row = this.row;
        int col = this.col;

        if(tileExists(tileBoard, row-1, col))
        {
            if((tileBoard[row-1][col] instanceof PathTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row, col-1))
        {
            if((tileBoard[row][col-1] instanceof PathTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row, col+1))
        {
            if((tileBoard[row][col+1] instanceof PathTile))
            {
                counter++;
            }
        }

        if(tileExists(tileBoard, row+1, col))
        {
            if((tileBoard[row+1][col] instanceof PathTile))
            {
                counter++;
            }
        }

        if(counter == 4)
            return false;
        else return true;
    }
}