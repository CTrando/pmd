package com.mygdx.pmd.model.Factory;


import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.ConnectFrom;
import com.mygdx.pmd.model.FloorComponent.Connector;
import com.mygdx.pmd.model.FloorComponent.Path;
import com.mygdx.pmd.model.FloorComponent.Room;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.PRandomInt;

import java.lang.ref.PhantomReference;


/**
 * Created by Cameron on 12/11/2016.
 */
public class FloorFactory {
    Controller controller;
    public Tile[][] tileBoard;
    Tile[][] placeHolder;
    Array<Room> rooms;
    private Array<Connector> connectors;

    public boolean stopGenerating = false;

    public int NUM_ROOMS = 10;

    public FloorFactory(Controller controller){
        this.controller = controller;
        tileBoard = new Tile[DungeonScreen.windowRows][DungeonScreen.windowCols];
        placeHolder = new Tile[tileBoard.length][tileBoard[0].length];
        rooms = new Array<Room>();
        connectors = new Array<Connector>();
    }

    public Tile[][] createFloor(){
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

    public void createRooms(int NUM_ROOMS){
        for(int i = 0; i< NUM_ROOMS; i++){
            Room room = new Room(this);
            room.createRoom();
            rooms.add(room);
        }
    }

    public void createConnections(){
        while(connectors.size > 0 && !stopGenerating){
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
            } else {
                Path path = new Path(this, connector);
                path.createPath();
            }

            if(rooms.size > NUM_ROOMS) {
                stopGenerating = true;
            }

        }
    }

    public void createPaths(){
        for(int i = 0; i< 15; i++){
            Path path = new Path(this,connectors.pop());
            path.createPath();
        }
    }

    public void finalizeFloor(){
        for(int i = 0; i< placeHolder.length; i++){
            for(int j = 0; j< tileBoard[0].length; j++){
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
