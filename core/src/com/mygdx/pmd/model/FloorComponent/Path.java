package com.mygdx.pmd.model.FloorComponent;

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
    public FloorFactory floorFactory;
    Tile[][] placeHolder;
    private int originRow;
    private int originCol;

    private int rowOffset;
    private int colOffset;


    public Path(FloorFactory floorFactory, Connector connector) {
        this.connector = connector;
        this.floorFactory = floorFactory;
        this.origin = connector.tile;
        this.placeHolder = floorFactory.getPlaceHolder();

        originRow = origin.row;
        originCol = origin.col;
    }

    public void createPath() {
        int pathSize = PRandomInt.random(2, 5);

        switch (connector.direction) {
            case up:
                for (int i = originRow; i < originRow + pathSize; i++) {
                    placeHolder[i][originCol] = new DoorTile(i, originCol, floorFactory);
                }
                break;
            case down:
                for (int i = originRow - pathSize; i < originRow; i++) {
                    placeHolder[i][originCol] = new DoorTile(i, originCol, floorFactory);
                }
                break;
            case left:
                for (int i = originCol - pathSize; i < originCol; i++) {
                    placeHolder[i][originCol] = new DoorTile(originRow, i, floorFactory);
                }
                break;
            case right:
                for (int i = originCol; i < originCol + pathSize; i++) {
                    placeHolder[i][originCol] = new DoorTile(originRow, i, floorFactory);
                }
                break;
        }

    }

}
