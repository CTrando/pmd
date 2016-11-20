package com.mygdx.pmd.Model.FloorComponent;


import com.mygdx.pmd.Model.TileType.DoorTile;
import com.mygdx.pmd.Model.TileType.RoomTile;
import com.mygdx.pmd.Model.TileType.Tile;
import com.mygdx.pmd.utils.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Cameron on 6/17/2016.
 */
public class Room {
    private ArrayList<Tile> roomConstraints;

    public int startingRow;   //height and width are expressed in tiles
    public int startingCol;

    public int endingRow;
    public int endingCol;

    public DoorTile door;
    private Floor floor;
    public Tile[][] tileBoard;

    private int windowWidthinTiles;
    private int windowLengthinTiles;

    public static final int ROOM_SMALL = 20;
    public static final int ROOM_MEDIUM = 30;
    public static final int ROOM_LARGE = 40;

    public Room(int startingRow, int startingCol, int endingRow, int endingCol, Floor floor) {
        this.floor = floor;
        this.tileBoard = floor.getTileBoard();
        this.startingCol = startingCol;
        this.startingRow = startingRow;

        this.endingCol = endingCol;
        this.endingRow = endingRow;

        windowLengthinTiles = floor.getWindowRows();
        windowWidthinTiles = floor.getWindowCols();

        roomConstraints = new ArrayList<Tile>();

        this.initializeRoomConstraints();
    }

    public Room(Floor floor) {
        this.floor = floor;

        roomConstraints = new ArrayList<Tile>();
        tileBoard = floor.getTileBoard();

        windowLengthinTiles = floor.getWindowRows();
        windowWidthinTiles = floor.getWindowCols();

        startingRow = ((int)(windowLengthinTiles*Math.random()));
        startingCol = ((int)(windowWidthinTiles*Math.random()));

        endingRow = ((int)((2*windowLengthinTiles +1)*Math.random())) - (windowLengthinTiles-15);
        endingCol = ((int)((2*windowWidthinTiles +1)*Math.random())) - (windowWidthinTiles-15);

        this.checkRoomBounds();
        this.isRoomLegal();

        this.initializeRoomConstraints();
    }

    public void initializeRoomConstraints()
    {
        this.isRoomLegal();
        roomConstraints = new ArrayList<Tile>();
        for(int row = startingRow; row< endingRow; row++)
        {
            for(int col = startingCol; col<endingCol; col++ )
            {
                RoomTile r = new RoomTile(row ,col,this,floor);
                roomConstraints.add(r);
            }

        }

        int rand = (int)((roomConstraints.size())* Math.random());
        door = new DoorTile(roomConstraints.get(rand).row, roomConstraints.get(rand).col, floor, this);
        roomConstraints.set(rand, door);
    }

    public ArrayList<Tile> getRoomConstraints()
    {
        return roomConstraints;
    }


    public boolean overlaps(Room other)
    {
        for(Tile o: this.roomConstraints)
        {
            for(Tile z: other.getRoomConstraints())
            {
                if(o.col == z.col && o.row == z.row)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkRoomBounds() {

        if(startingCol < 0)
        {
            startingCol = 0;
        }

        if(startingRow < 0)
        {
            startingCol = 0;
        }

        if(startingCol > windowWidthinTiles)
        {
            startingRow = windowWidthinTiles;
        }

        if(startingRow > windowLengthinTiles)
        {
            startingRow = windowLengthinTiles;
        }

        if (endingRow < 0) {
            endingRow = 0;
        }

        if (endingCol < 0) {
            endingCol = 0;
        }

        if (endingRow > windowLengthinTiles) {
            endingRow = windowLengthinTiles;

        }

        if (endingCol > windowWidthinTiles) {
            endingCol = windowWidthinTiles;
        }
    }

    public void isRoomLegal() {

        if(endingRow == startingRow+1)
        {
            endingRow +=2;
            endingCol +=2;
        }

        if(endingCol == startingCol+1)
        {
            endingCol +=2;
            endingRow +=2;
        }

        if (endingRow < startingRow) {
            int x = startingRow;
            startingRow = endingRow;
            endingRow = x;
        }

        if (endingCol < startingCol) {
            int x = startingCol;
            startingCol = endingCol;
            endingCol = x;
        }


        this.checkRoomBounds();
    }

    public void moveRoom()
    {
        int rand = (int)(5*Math.random());

        startingCol+=rand;
        endingCol+=rand;
        startingRow+=rand;
        startingCol+=rand;
        this.checkRoomBounds();
    }
}
