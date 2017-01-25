package com.mygdx.pmd.model.Floor;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.ConnectFrom;
import com.mygdx.pmd.enumerations.Direction;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Tile.DoorTile;
import com.mygdx.pmd.model.Tile.RoomTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.utils.PRandomInt;

/**
 * Created by Cameron on 12/15/2016.
 */
public class Path {
    public Connector connector;
    public Tile origin;
    public Tile terminal;
    public Array<Tile> pathConstraints;

    public FloorFactory floorFactory;
    public Floor floor;

    Tile[][] placeHolder;
    private int originRow;
    private int originCol;


    public Path(FloorFactory floorFactory, Floor floor, Connector connector) {
        this.connector = connector;
        this.floorFactory = floorFactory;
        this.floor = floor;

        this.origin = connector.tile;
        this.placeHolder = floorFactory.getPlaceHolder();
        this.pathConstraints = new Array<Tile>();

        originRow = origin.row;
        originCol = origin.col;
    }

    public void createPath() {
        int pathSize = PRandomInt.random(8, 10);

        switch (connector.direction) {
            case up:
                if (originRow + pathSize >= placeHolder.length) pathSize = placeHolder.length - originRow;

                for (int i = originRow; i < originRow + pathSize; i++) {
                    placeHolder[i][originCol] = new RoomTile(i, originCol, floor);
                    pathConstraints.add(placeHolder[i][originCol]);
                }
                this.terminal = placeHolder[originRow + pathSize-1][originCol];
                break;
            case down:
                if (originRow - pathSize <= 0) pathSize = originRow;

                for (int i = originRow; i >= originRow - pathSize; i--) {
                    placeHolder[i][originCol] = new RoomTile(i, originCol, floor);
                    pathConstraints.add(placeHolder[i][originCol]);
                }
                this.terminal = placeHolder[originRow - pathSize+1][originCol];
                break;
            case left:
                if (originCol - pathSize <= 0) pathSize = originCol;

                for (int i = originCol; i >= originCol - pathSize; i--) {
                    placeHolder[originRow][i] = new RoomTile(originRow, i, floor);
                    pathConstraints.add(placeHolder[originRow][i]);
                }
                this.terminal = placeHolder[originRow][originCol - pathSize+1];
                break;
            case right:
                if (originCol + pathSize >= placeHolder[0].length) pathSize = placeHolder.length - originCol;

                for (int i = originCol; i < originCol + pathSize; i++) {
                    placeHolder[originRow][i] = new RoomTile(originRow, i, floor);
                    pathConstraints.add(placeHolder[originRow][i]);
                }
                this.terminal = placeHolder[originRow][originCol + pathSize-1];
                break;
        }
        if(!(this.terminal instanceof DoorTile)) {
            System.out.println("ERROR");
        }

        this.setConnectors();
    }

    public void setConnectors() {
        Connector connector = new Connector(this.terminal, this.getDirection(), ConnectFrom.PATH);
        floorFactory.addConnector(connector);
    }

    public Direction getDirection() {
        Direction retDir = getRandomDirection();
        if (retDir != connector.direction.getOppositeDirection()) {
            return retDir;
        } else {
            retDir = getDirection();
        }
        return retDir;
    }

    public Direction getRandomDirection() {
        int rand = PRandomInt.random(0, 3); //possible error here with PRandomInt
        switch (rand) {
            case 0:
                return Direction.up;
            case 1:
                return Direction.down;
            case 2:
                return Direction.left;
            case 3:
                return Direction.right;
        }
        return null;
    }

}
