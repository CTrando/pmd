package com.mygdx.pmd.model.FloorComponent;


import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.ConnectFrom;
import com.mygdx.pmd.enumerations.Direction;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Tile.DoorTile;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PRandomInt;

import java.util.ArrayList;

/**
 * Created by Cameron on 6/17/2016.
 */
public class Room {
    public ArrayList<Tile> roomConstraints;

    public int startingRow;   //HEIGHT and width are expressed in tiles
    public int startingCol;

    public int width;
    public int height;

    public FloorFactory floorFactory;
    public Tile[][] placeHolder;
    public Array<Tile> borderTiles;

    public Room(FloorFactory floorFactory){
        this.floorFactory = floorFactory;
        this.placeHolder = floorFactory.getPlaceHolder();

        startingRow = PRandomInt.random(0, placeHolder.length);
        startingCol = PRandomInt.random(0, placeHolder[0].length);

        height = PRandomInt.random(3,10);
        width = PRandomInt.random(3, 10);

        if(startingRow + height > placeHolder.length) startingRow = 0;
        if(startingCol + width > placeHolder[0].length) startingCol = 0;

        //set up border tiles - perimeter of rectangle
        this.borderTiles = new Array<Tile>();
    }

    public Room(FloorFactory floorFactory, Connector connector){
        this.floorFactory = floorFactory;
        this.placeHolder = floorFactory.getPlaceHolder();

        startingRow = connector.tile.row;
        startingCol= connector.tile.col;

        height = PRandomInt.random(2,3);
        width = PRandomInt.random(2, 3);

        if(startingRow + height > placeHolder.length) height = placeHolder.length - startingRow -1;
        if(startingCol + width > placeHolder[0].length) width = placeHolder[0].length - startingCol -1;

        //set up border tiles - perimeter of rectangle
        this.borderTiles = new Array<Tile>();
    }

    public void createRoom(){
        for(int i = startingRow; i< startingRow+height; i++){
            for(int j = startingCol; j< startingCol + width; j++){
                placeHolder[i][j] = new RoomTile(i,j,floorFactory);

                if( i == startingRow ||
                    i == startingRow+height-1 ||
                    j == startingCol ||
                    j == startingCol+width-1 )
                    borderTiles.add(placeHolder[i][j]);
            }
        }
        this.setConnectors();
    }

    public void setConnectors(){
        if(borderTiles.size <= 0) return;
        int numConnectors = PRandomInt.random(1,3);

        for(int i = 0; i< numConnectors; i++){
            int randIndex = PRandomInt.random(0,borderTiles.size-1);
            Tile randTile = borderTiles.get(randIndex);
            placeHolder[randTile.row][randTile.col] = new DoorTile(randTile.row, randTile.col, floorFactory);
            if(randTile == null) System.out.println("ROOM TILE TERRIBLE");


            Connector connector = new Connector(randTile, getDirection(randTile), ConnectFrom.ROOM);
            floorFactory.addConnector(connector);
        }
    }

    public Direction getDirection(Tile borderTile){
        if(borderTile.row == startingRow) return Direction.down;
        if(borderTile.row == startingRow+height-1) return Direction.up;
        if(borderTile.col == startingCol) return Direction.left;
        if(borderTile.col == startingCol+width-1) return Direction.right;
        return null;
    }
}
