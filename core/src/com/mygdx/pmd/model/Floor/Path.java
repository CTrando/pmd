package com.mygdx.pmd.model.Floor;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Factory.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.utils.PRandomInt;

/**
 * Created by Cameron on 12/15/2016.
 */
public class Path {
    private FloorFactory floorFactory;
    private Floor floor;

    private Connector connector;
    private Tile originTile;
    private Tile terminalTile;
    private Array<Tile> pathConstraints;

    private Tile[][] placeHolder;
    private int originRow;
    private int originCol;

    public Path(FloorFactory floorFactory, Floor floor, Connector connector) {
        this.connector = connector;
        this.floorFactory = floorFactory;
        this.floor = floor;

        this.originTile = connector.tile;
        this.placeHolder = floorFactory.getPlaceHolder();
        this.pathConstraints = new Array<Tile>();

        originRow = originTile.row;
        originCol = originTile.col;
    }

    public void createPath() {
        int pathSize = PRandomInt.random(8, 10);

        switch (connector.direction) {
            case up:
                if (originRow + pathSize >= placeHolder.length - 1) pathSize = (placeHolder.length - 1) - originRow;

                for (int i = originRow; i < originRow + pathSize; i++) {
                    placeHolder[i][originCol] = new RoomTile(i, originCol, floor);
                    pathConstraints.add(placeHolder[i][originCol]);
                }
                this.terminalTile = placeHolder[originRow + pathSize-1][originCol];
                break;
            case down:
                if (originRow - pathSize <= 1) pathSize = originRow - 1;

                for (int i = originRow; i >= originRow - pathSize; i--) {
                    placeHolder[i][originCol] = new RoomTile(i, originCol, floor);
                    pathConstraints.add(placeHolder[i][originCol]);
                }
                this.terminalTile = placeHolder[originRow - pathSize+1][originCol];
                break;
            case left:
                if (originCol - pathSize <= 1) pathSize = originCol - 1;

                for (int i = originCol; i >= originCol - pathSize; i--) {
                    placeHolder[originRow][i] = new RoomTile(originRow, i, floor);
                    pathConstraints.add(placeHolder[originRow][i]);
                }
                this.terminalTile = placeHolder[originRow][originCol - pathSize+1];
                break;
            case right:
                if (originCol + pathSize >= placeHolder[0].length-1) pathSize = (placeHolder.length - 1) - originCol;

                for (int i = originCol; i < originCol + pathSize; i++) {
                    placeHolder[originRow][i] = new RoomTile(originRow, i, floor);
                    pathConstraints.add(placeHolder[originRow][i]);
                }
                this.terminalTile = placeHolder[originRow][originCol + pathSize-1];
                break;
        }

        this.setConnectors();
    }

    private void setConnectors() {
        Connector connector = new Connector(this.terminalTile, this.getDirection(), ConnectFrom.PATH);
        floorFactory.addConnector(connector);
    }

    private Direction getDirection() {
        Direction retDir = getRandomDirection();
        if (retDir != connector.direction.getOpposite()) {
            return retDir;
        } else {
            retDir = getDirection();
        }
        return retDir;
    }

    private Direction getRandomDirection() {
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
