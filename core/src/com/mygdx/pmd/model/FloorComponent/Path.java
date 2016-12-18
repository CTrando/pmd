package com.mygdx.pmd.model.FloorComponent;

import com.mygdx.pmd.enumerations.ConnectFrom;
import com.mygdx.pmd.enumerations.Direction;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Tile.DoorTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PRandomInt;

/**
 * Created by Cameron on 12/15/2016.
 */
public class Path {
    public Connector connector;
    public Tile origin;
    public Tile terminal;

    public FloorFactory floorFactory;
    Tile[][] placeHolder;
    private int originRow;
    private int originCol;


    public Path(FloorFactory floorFactory, Connector connector) {
        this.connector = connector;
        this.floorFactory = floorFactory;
        this.origin = connector.tile;
        this.placeHolder = floorFactory.getPlaceHolder();

        originRow = origin.row;
        originCol = origin.col;
    }

    public void createPath() {
        int pathSize = PRandomInt.random(2, 15);

        switch (connector.direction) {
            case up:
                if (originRow + pathSize >= placeHolder.length) pathSize = placeHolder.length - originRow - 1;

                for (int i = originRow; i < originRow + pathSize; i++) {
                    placeHolder[i][originCol] = new DoorTile(i, originCol, floorFactory);
                }
                this.terminal = placeHolder[originRow+pathSize][originCol];

                break;
            case down:
                if (originRow - pathSize <= 0) pathSize = originRow - pathSize - 1;

                for (int i = originRow; i > originRow - pathSize; i--) {
                    placeHolder[i][originCol] = new DoorTile(i, originCol, floorFactory);
                }
                this.terminal = placeHolder[originRow-pathSize][originCol];
                break;
            case left:
                if (originCol - pathSize <= 0) pathSize = originCol - pathSize - 1;

                for (int i = originCol; i > originCol - pathSize; i--) {
                    placeHolder[originRow][i] = new DoorTile(originRow, i, floorFactory);
                }
                this.terminal = placeHolder[originRow][originCol - pathSize];
                break;
            case right:
                if (originCol + pathSize >= placeHolder[0].length) pathSize = placeHolder.length - originCol - 1;

                for (int i = originCol; i < originCol + pathSize; i++) {
                    placeHolder[originRow][i] = new DoorTile(originRow, i, floorFactory);
                }
                this.terminal = placeHolder[originRow][originCol + pathSize];
                break;
        }
        if(connector.direction == null) System.out.println("DIRECTION NULL");
        if(this.terminal == null) System.out.println("TERRIBLE ");
        this.setConnectors();
    }

    public void setConnectors() {
        Connector connector = new Connector(this.terminal, this.getDirection(), ConnectFrom.PATH);
        floorFactory.addConnector(connector);
    }

    public Direction getDirection() {
        Direction retDir = null;
        int rand = PRandomInt.random(0, 3); //possible error here with PRandomInt
        switch (rand) {
            case 0:
                retDir = Direction.up;
                break;
            case 1:
                retDir = Direction.down;
                break;
            case 2:
                retDir = Direction.left;
                break;
            case 3:
                retDir = Direction.right;
                break;
        }
        if (retDir != null)
            return retDir;
        else return Direction.right;
    }

}
