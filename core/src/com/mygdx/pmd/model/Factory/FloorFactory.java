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
import com.mygdx.pmd.utils.PRandomInt;


/**
 * Created by Cameron on 12/11/2016.
 */

/**
 * TODO Potential problem with placing rooms right next to each other
 * TODO Fix item not being cleared
 */
public class FloorFactory {
    Tile[][] placeHolder;

    Array<com.mygdx.pmd.model.Floor.Room> rooms;
    private Array<com.mygdx.pmd.model.Floor.Connector> connectors;

    public boolean stopGenerating = false;
    Floor floor;

    public FloorFactory(Controller controller){
        floor = new Floor(controller);
        connectors = new Array<Connector>();
        rooms = new Array<Room>();
    }

    public Floor createFloor(Controller controller){
        //function needed to clear floor

        placeHolder = new Tile[DungeonScreen.windowRows][DungeonScreen.windowCols];

        //reset variables - should probably change this
        connectors.clear();
        rooms.clear();
        stopGenerating = false;

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
        while(connectors.size > 0 && connectors.size < 20){
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

            System.out.println(connectors.size);
        }
        System.out.println("Done");
    }

    private void placeTiles(Floor floor){
        // set sprite based on spriteValue and move placeHolder to tileBoard
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

   /* public boolean isWithinBounds(int row, int col){
        if(row >= tileBoard.length || row < 0) return false;
        if(col >= tileBoard[0].length || col < 0) return false;
        return true;
    }*/

    public Tile[][] getPlaceHolder() {
        return placeHolder;
    }
}
