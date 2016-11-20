package com.mygdx.pmd.Model.Generator;


import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Model.FloorComponent.Room;
import com.mygdx.pmd.Model.TileType.RoomTile;
import com.mygdx.pmd.Model.TileType.Tile;

import java.util.ArrayList;

/**
 * Created by Cameron on 7/4/2016.
 */
public class RoomGenerator {
    private ArrayList<Room> roomList;
    private int windowRows;
    private int windowCols;
    private int roomNum;

    private Floor floor;
    private Tile[][] tileBoard;

    private ArrayList<Tile> roomTileList;
    private ArrayList<Tile> doorList;


    public RoomGenerator(Floor floor) {
        roomList = new ArrayList<Room>();
        roomTileList = new ArrayList<Tile>();
        doorList = new ArrayList<Tile>();

        tileBoard = floor.getTileBoard();

        this.windowRows = floor.getWindowRows();
        this.windowCols = floor.getWindowCols();
        this.roomNum = floor.NUMBER_OF_ROOMS;
        this.floor = floor;
    }

    public void generateRooms() {
        roomList = new ArrayList<Room>();
        roomTileList = new ArrayList<Tile>();
        doorList = new ArrayList<Tile>();

        int currentNumberOfRooms = 0;
        while(currentNumberOfRooms < roomNum) {

            int startingRow = (int) (windowRows* Math.random());
            int startingCol = (int) (windowCols * Math.random());

            int width = (int) (10 * Math.random())+1;
            int height = (int) (10 * Math.random())+1;

            int endingRow = startingRow + height;
            int endingCol = startingCol + width;

            Room room = new Room(startingRow, startingCol, endingRow, endingCol, floor);

            if(!this.listOverlaps(room)) {
                roomList.add(room);
                doorList.add(room.door);
                currentNumberOfRooms++;
            }
        }
        this.placeRoomTiles();
    }


    public void placeRoomTiles() {
        for (Room r : roomList) {
            for (Tile rt : r.getRoomConstraints()) {
                tileBoard[rt.row][rt.col] = rt;
                if(rt instanceof RoomTile)
                    roomTileList.add(rt);
            }
        }
    }

    public boolean listOverlaps(Room room)
    {
        for(Room r: roomList)
        {
            if(room.overlaps(r))
            {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public ArrayList<Tile> getRoomTileList() {
        return roomTileList;
    }

    public ArrayList<Tile> getDoorList() {
        return doorList;
    }
}

