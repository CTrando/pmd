package com.mygdx.pmd.model.Factory;


import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.ConnectFrom;
import com.mygdx.pmd.model.Floor.Connector;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.model.Floor.Path;
import com.mygdx.pmd.model.Floor.Room;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.*;


/**
 * Created by Cameron on 12/11/2016.
 */

/**
 * TODO Potential problem with placing rooms right next to each other
 * TODO Fix item not being cleared
 */
public class FloorFactory {
    private Array<Room> rooms;
    private Array<Connector> connectors;

    private Floor floor;
    private Tile[][] placeHolder;

    public FloorFactory(Controller controller){
        floor = new Floor(controller);
        connectors = new Array<Connector>();
        rooms = new Array<Room>();
    }

    public Floor createFloor(Controller controller){
        //function needed to clear floor
        floor.clear();
        placeHolder = new Tile[Constants.tileBoardRows][Constants.tileBoardCols];

        //reset variables - should probably change this
        connectors.clear();
        rooms.clear();

        this.createBlankFloor(floor);
        //create the first room
        Room room = new Room(this, floor);
        room.createRoom();

        //create the first path
        Path path = new Path(this, floor, connectors.pop());
        path.createPath();

        //start updating the connectors
        this.createConnections(floor);

        //place tiles
        this.placeTiles(floor);

        return floor;
    }

    private void createBlankFloor(Floor floor){
        for(int i = 0; i< placeHolder.length; i++){
            for(int j = 0; j< placeHolder[0].length; j++){
                placeHolder[i][j] = new GenericTile(i,j,floor);
            }
        }
    }

    private void createConnections(Floor floor){
        System.out.println("Beginning path generation...");
        while(connectors.size > 0 && connectors.size < Constants.MAX_CONNCETORS){
            Connector connector = connectors.pop();

            if (connector.connectFrom == ConnectFrom.PATH){
                int rand = PRandomInt.random(0,1);
                if(rand == 0){
                    Path path = new Path(this, floor, connector);
                    path.createPath();
                } else {
                    Room room = new Room(this, floor, connector);
                    room.createRoom();
                    rooms.add(room);
                }
            } else if (connector.connectFrom == ConnectFrom.ROOM){
                Path path = new Path(this, floor, connector);
                path.createPath();
            }

            System.out.println(connectors.size + " connectors");
        }
        System.out.println("Done generating");
    }

    private void placeTiles(Floor floor){
        // set sprite based on spriteValue and currentMove placeHolder to tileBoard
        for(int i = 0; i< placeHolder.length; i++) {
            for (int j = 0; j < placeHolder.length; j++) {
                Tile tile = placeHolder[i][j];
                floor.tileBoard[i][j] = tile;
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
