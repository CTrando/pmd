package com.mygdx.pmd.model.Floor;


import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.ConnectFrom;
import com.mygdx.pmd.enumerations.Direction;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PRandomInt;

import java.util.ArrayList;

/**
 * Created by Cameron on 6/17/2016.
 */
public class Room {

    private int startingRow;   //HEIGHT and width are expressed in tiles
    private int startingCol;

    private int width;
    private int height;

    private FloorFactory floorFactory;
    private Floor floor;

    private Tile[][] placeHolder;
    private Array<Tile> borderTiles;

    private Direction orientation;

    public Room(FloorFactory floorFactory, Floor floor){
        this.floorFactory = floorFactory;
        this.floor = floor;
        this.placeHolder = floorFactory.getPlaceHolder();

        startingRow = PRandomInt.random(1, placeHolder.length);
        startingCol = PRandomInt.random(1, placeHolder[0].length);

        height = PRandomInt.random(2,3);
        width = PRandomInt.random(2, 3);

        if(startingRow + height > placeHolder.length) startingRow = 1;
        if(startingCol + width > placeHolder[0].length) startingCol = 1;

        //setup border tiles - perimeter of rectangle
        this.borderTiles = new Array<Tile>();
    }

    public Room(FloorFactory floorFactory, Floor floor, Connector connector){
        this.floorFactory = floorFactory;
        this.floor = floor;

        this.placeHolder = floorFactory.getPlaceHolder();
        this.orientation = connector.direction;

        startingRow = connector.tile.row;
        startingCol= connector.tile.col;

        height = PRandomInt.random(2,3);
        width = PRandomInt.random(2, 3);

        switch(orientation){
            case down: startingRow -= height; break;
            case left: startingCol -= width; break;
        }

        if(startingRow <= 1) startingRow = 1;
        if(startingCol <= 1) startingCol = 1;
        if(startingRow + height >= placeHolder.length -1) startingRow = (placeHolder.length - 1) - height;
        if(startingCol + width >= placeHolder[0].length -1) startingCol = (placeHolder[0].length - 1)- width;

        //setup border tiles - perimeter of rectangle
        this.borderTiles = new Array<Tile>();
    }

    public void createRoom(){
        for(int i = startingRow; i< startingRow+height; i++){
            for(int j = startingCol; j< startingCol + width; j++){
                placeHolder[i][j] = new RoomTile(i,j, floor);

                if( i == startingRow ||
                    i == startingRow+height-1 ||
                    j == startingCol ||
                    j == startingCol+width-1 )
                    borderTiles.add(placeHolder[i][j]);
            }
        }
        this.setConnectors();
    }

    private void setConnectors(){
        if(borderTiles.size <= 0) return;
        int numConnectors = PRandomInt.random(1,3);

        for(int i = 0; i< numConnectors; i++){
            if(borderTiles.size == 0) break;

            //find random tile and set it to a connector
            int randIndex = PRandomInt.random(0,borderTiles.size-1);
            Tile randTile = borderTiles.get(randIndex);
            placeHolder[randTile.row][randTile.col] = new RoomTile(randTile.row, randTile.col, floor);
            borderTiles.removeValue(randTile,false);

            Array<Tile> neighbors = Tile.getTilesAroundTile(placeHolder,randTile);
            borderTiles.removeAll(neighbors,false);

            Connector connector = new Connector(randTile, getDirection(randTile), ConnectFrom.ROOM);
            floorFactory.addConnector(connector);
        }
    }


    private Direction getDirection(Tile borderTile){
        if(borderTile.row == startingRow) return Direction.down;
        if(borderTile.row == startingRow+height-1) return Direction.up;
        if(borderTile.col == startingCol) return Direction.left;
        if(borderTile.col == startingCol+width-1) return Direction.right;
        return null;
    }
}
