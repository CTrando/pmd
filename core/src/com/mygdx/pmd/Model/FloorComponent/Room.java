package com.mygdx.pmd.model.FloorComponent;


import com.mygdx.pmd.controller.Controller;
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

    public Controller controller;
    public Tile[][] tileBoard;

    public Room(Controller controller, Tile[][] tileBoard){
        this.controller = controller;
        this.tileBoard = tileBoard;

        startingRow = PRandomInt.random(0, tileBoard.length);
        startingCol = PRandomInt.random(0, tileBoard[0].length);

        height = PRandomInt.random(0,10);
        width = PRandomInt.random(0, 10);

        startingRow = (startingRow+height)%tileBoard.length;
        startingCol = (startingCol+width)%tileBoard[0].length;
    }

    public void createRoom(){
        for(int i = startingRow; i< startingRow+height; i++){
            for(int j = startingCol; j< startingCol + width; j++){
                tileBoard[i][j] = new RoomTile(i,j, controller, this);
            }
        }
    }
}
