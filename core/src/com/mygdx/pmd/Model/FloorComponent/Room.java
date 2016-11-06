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

    private int startingRow;   //height and width are expressed in tiles
    private int startingCol;

    private int area;

    private int endingRow;
    private int endingCol;

    private Floor floor;

    private int windowWidthinTiles;
    private int windowLengthinTiles;

    private Color color;

    private Tile[][] tileBoard;

    public static final int ROOM_SMALL = 20;
    public static final int ROOM_MEDIUM = 30;
    public static final int ROOM_LARGE = 40;

    private DoorTile door;

    public Room(int startingRow, int startingCol, int endingRow, int endingCol, Floor floor) {
        this.floor = floor;
        this.startingCol = startingCol;
        this.startingRow = startingRow;

        windowLengthinTiles = floor.getWindowRows();
        windowWidthinTiles = floor.getWindowCols();

        this.endingCol = endingCol;
        this.endingRow = endingRow;

        roomConstraints = new ArrayList<Tile>();

        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();

        color = new Color(red,green,blue);
        tileBoard = floor.getTileBoard();

        this.initializeRoomConstraints();
    }

    public Room(Floor floor) {
        this.floor = floor;

        roomConstraints = new ArrayList<Tile>();
        tileBoard = floor.getTileBoard();

        windowLengthinTiles = floor.getWindowRows()/Constants.TILE_SIZE;
        windowWidthinTiles = floor.getWindowCols()/ Constants.TILE_SIZE;

        startingRow = ((int)(windowLengthinTiles*Math.random()));
        startingCol = ((int)(windowWidthinTiles*Math.random()));

        endingRow = ((int)((2*windowLengthinTiles +1)*Math.random())) - (windowLengthinTiles-15);
        endingCol = ((int)((2*windowWidthinTiles +1)*Math.random())) - (windowWidthinTiles-15);


        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();

        color = new Color(red,green,blue);

    /*    endingRow = startingRow+5;
        endingCol = startingCol+5;*/

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
                RoomTile r = new RoomTile(floor, row ,col , "Room Tile", color, this);
                roomConstraints.add(r);
            }

        }

        int rand = (int)((roomConstraints.size())* Math.random());
        door = new DoorTile(floor, roomConstraints.get(rand).row, roomConstraints.get(rand).col, this);
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

    public int getStartingRow() {
        return startingRow;
    }

    public void setStartingRow(int startingRow) {
        this.startingRow = startingRow;
    }

    public int getStartingCol() {
        return startingCol;
    }

    public void setStartingCol(int startingCol) {
        this.startingCol = startingCol;
    }

    public int getEndingRow() {
        return endingRow;
    }

    public void setEndingRow(int endingRow) {
        this.endingRow = endingRow;
    }

    public int getEndingCol() {
        return endingCol;
    }

    public void setEndingCol(int endingCol) {
        this.endingCol = endingCol;
    }

    public DoorTile getDoor() {
        return door;
    }
}
