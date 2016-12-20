package com.mygdx.pmd.model.Factory;


import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.ConnectFrom;
import com.mygdx.pmd.model.FloorComponent.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.PRandomInt;


/**
 * Created by Cameron on 12/11/2016.
 */

/**
 * TODO Potential problem with placing rooms right next to each other
 */
public class FloorFactory {
    Controller controller;
    public Tile[][] tileBoard;
    Tile[][] placeHolder;
    Array<Room> rooms;
    private Array<Connector> connectors;

    public boolean stopGenerating = false;

    public FloorFactory(Controller controller){
        this.controller = controller;
        tileBoard = new Tile[DungeonScreen.windowRows][DungeonScreen.windowCols];
        placeHolder = new Tile[tileBoard.length][tileBoard[0].length];
        rooms = new Array<Room>();
        connectors = new Array<Connector>();
    }

    public Tile[][] createFloor(){
        //reset variables - should probably change this
        connectors = new Array<Connector>();
        rooms = new Array<Room>();
        stopGenerating = false;

        this.createBlankFloor();
        //create the first room
        Room room = new Room(this);
        room.createRoom();

        //create the first path
        Path path = new Path(this,connectors.pop());
        path.createPath();

        //start updating the connectors
        this.createConnections();

        // do sprite settings and placeholder --> tileBoard
        this.finalizeFloor();
        return tileBoard;
    }

    public void createBlankFloor(){
        for(int i = 0; i< placeHolder.length; i++){
            for(int j = 0; j< placeHolder[0].length; j++){
                placeHolder[i][j] = new GenericTile(i,j,this);
            }
        }
    }

    public void createConnections(){
        while(connectors.size > 0 && connectors.size < 20){
            Connector connector = connectors.pop();

            if (connector.connectFrom == ConnectFrom.PATH){
                int rand = PRandomInt.random(0,1);
                if(rand == 0){
                    Path path = new Path(this, connector);
                    path.createPath();
                } else {
                    Room room = new Room(this, connector);
                    room.createRoom();
                    rooms.add(room);
                }
            } else if (connector.connectFrom == ConnectFrom.ROOM){
                Path path = new Path(this, connector);
                path.createPath();
            }

            System.out.println(connectors.size);
        }
        System.out.println("Done");
    }

    public void finalizeFloor(){
        //check neighbors and set spriteValue
        for(int i = 0; i< placeHolder.length; i++){
            for(int j = 0; j< placeHolder[0].length; j++){
                if(this.isWithinBounds(i+1,j) && placeHolder[i+1][j] instanceof RoomTile) placeHolder[i][j].spriteValue+=1;
                if(this.isWithinBounds(i+1, j+1) && placeHolder[i+1][j+1] instanceof RoomTile) placeHolder[i][j].spriteValue+=2;
                if(this.isWithinBounds(i,j+1) && placeHolder[i][j+1] instanceof RoomTile) placeHolder[i][j].spriteValue+=3;
                if(this.isWithinBounds(i-1, j+1) && placeHolder[i-1][j+1] instanceof RoomTile) placeHolder[i][j].spriteValue+=4;
                if(this.isWithinBounds(i-1,j) && placeHolder[i-1][j] instanceof RoomTile) placeHolder[i][j].spriteValue+=5;
                if(this.isWithinBounds(i-1, j-1) && placeHolder[i-1][j-1] instanceof RoomTile) placeHolder[i][j].spriteValue+=6;
                if(this.isWithinBounds(i,j-1) && placeHolder[i][j-1] instanceof RoomTile) placeHolder[i][j].spriteValue+=7;
                if(this.isWithinBounds(i+1, j-1) && placeHolder[i+1][j-1] instanceof RoomTile) placeHolder[i][j].spriteValue+=8;
            }
        }

        // set sprite based on spriteValue and move placeHolder to tileBoard
        for(int i = 0; i< placeHolder.length; i++){
            for(int j = 0; j< tileBoard[0].length; j++){
                Tile tile = placeHolder[i][j];
                if(tile.spriteValue == 0)
                    tile.sprite = PMD.sprites.get("blacktilesprite");
                else if (tile.spriteValue == 18)
                    tile.sprite = PMD.sprites.get("toprighttilesprite");
                else if (tile.spriteValue == 16)
                    tile.sprite = PMD.sprites.get("bottomrightcornertilesprite");
                else if (tile.spriteValue == 12)
                    tile.sprite = PMD.sprites.get("toplefttilesprite");
                else if (tile.spriteValue == 6)
                    tile.sprite = PMD.sprites.get("bottomlefttilesprite");

                tileBoard[i][j] = placeHolder[i][j];
            }
        }

    }

    public void addConnector(Connector connector){
        connectors.add(connector);
    }

    public boolean isWithinBounds(int row, int col){
        if(row >= tileBoard.length || row < 0) return false;
        if(col >= tileBoard[0].length || col < 0) return false;
        return true;
    }

    public Tile[][] getPlaceHolder() {
        return placeHolder;
    }
}
