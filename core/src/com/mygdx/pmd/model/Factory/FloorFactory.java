package com.mygdx.pmd.model.Factory;


import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.FloorComponent.Connector;
import com.mygdx.pmd.model.FloorComponent.Path;
import com.mygdx.pmd.model.FloorComponent.Room;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;



/**
 * Created by Cameron on 12/11/2016.
 */
public class FloorFactory {
    Controller controller;
    public Tile[][] tileBoard;
    Tile[][] placeHolder;
    Array<Room> rooms;
    private Array<Connector> connectors;

    public int NUM_ROOMS = 1;

    public FloorFactory(Controller controller){
        this.controller = controller;
        tileBoard = new Tile[DungeonScreen.windowRows][DungeonScreen.windowCols];
        placeHolder = new Tile[tileBoard.length][tileBoard[0].length];
        rooms = new Array<Room>();
        connectors = new Array<Connector>();
    }

    public Tile[][] createFloor(){
        this.createBlankFloor();
        this.createRooms(NUM_ROOMS);
        this.createPaths();

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

    public void createPaths(){
        while(connectors.size > 0){
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

    public Tile[][] getPlaceHolder() {
        return placeHolder;
    }
}
