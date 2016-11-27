package com.mygdx.pmd.model.Generator;


import com.mygdx.pmd.model.FloorComponent.Floor;
import com.mygdx.pmd.model.FloorComponent.Room;
import com.mygdx.pmd.model.Tile.*;

import java.util.ArrayList;

/**
 * Created by Cameron on 7/5/2016.
 */
public class PathGenerator {
    private Floor floor;
    private ArrayList<Room> roomList;
    private ArrayList<Tile> roomTileList;
    private Tile[][] tileBoard;
    private ArrayList<Tile> doorList;

    private ArrayList<PathTile> pathList;

    private boolean isPathLegal;

    private ArrayList<Tile> checkedOffList;
    private ArrayList<Tile> processingList;

    public PathGenerator(Floor floor) {
        this.floor = floor;
        this.tileBoard = floor.getTileBoard();

        this.roomTileList = floor.getRoomTileList();
        this.roomList = floor.getRoomList();
        this.pathList = new ArrayList<PathTile>();
        this.doorList = floor.getDoorList();

        this.isPathLegal = false;
    }

    public ArrayList<Tile> copyRoomList() {
        ArrayList<Tile> clone = new ArrayList<Tile>();
        for (Tile t : floor.getRoomTileList()) {
            clone.add(t);
        }
        return clone;
    }

    public void generatePaths() {
        this.pathList = new ArrayList<PathTile>();
        this.roomTileList = this.copyRoomList();
        this.doorList = floor.getDoorList();

        for (Tile tile : doorList) {
            Tile closestTile = tile.calculateClosestNeighbor(doorList);
            this.makePath(tile, closestTile);
        }
        this.assertContinuity();
        this.initializePathList();
    }

    public void makePath(Tile one, Tile two) {
        Tile current = one;
        Tile destination = two;

        while (destination.row > current.row) {
            current = tileBoard[current.row + 1][current.col];

            if (!(current instanceof RoomTile)) {
                PathTileVertical tempVerticalTile = new PathTileVertical(floor, current.row, current.col);

                if (tempVerticalTile.isLegal()) {
                    tileBoard[current.row][current.col] = tempVerticalTile;
                    pathList.add(tempVerticalTile);
                }
            }
        }

        while (destination.col > current.col) {
            current = tileBoard[current.row][current.col + 1];

            if (!(current instanceof RoomTile)) {
                PathTileHorizontal tempHorizontalTile = new PathTileHorizontal(floor, current.row, current.col);

                if (tempHorizontalTile.isLegal()) {
                    tileBoard[current.row][current.col] = tempHorizontalTile;
                    pathList.add(tempHorizontalTile);
                }
            }
        }

        while (destination.row < current.row) {
            current = tileBoard[current.row - 1][current.col];

            if (!(current instanceof RoomTile)) {
                PathTileVertical tempVerticalTile = new PathTileVertical(floor, current.row, current.col);

                if (tempVerticalTile.isLegal()) {
                    tileBoard[current.row][current.col] = tempVerticalTile;
                    pathList.add(tempVerticalTile);
                }
            }
        }

        while (destination.col < current.col) {
            current = tileBoard[current.row][current.col - 1];

            if (!(current instanceof RoomTile)) {
                PathTileHorizontal tempHorizontalTile = new PathTileHorizontal(floor, current.row, current.col);

                if (tempHorizontalTile.isLegal()) {
                    tileBoard[current.row][current.col] = tempHorizontalTile;
                    pathList.add(tempHorizontalTile);
                }
            }
        }
    }

    public void initializePathList() {
        for (PathTile t : pathList) {
            tileBoard[t.row][t.col] = t;
        }
    }

    public void assertContinuity() {
        this.processingList = new ArrayList<Tile>();
        this.checkedOffList = new ArrayList<Tile>();

        int random = (int) (Math.random() * pathList.size());

        Tile starterTile = pathList.get(random);
        starterTile.print();

        processingList.add(starterTile);

        while (processingList.size() > 0) {
            for (int i = 0; i < processingList.size(); i++) {
                Tile t = processingList.get(i);

                this.evaluateTile(t);
                processingList.remove(t);
                if (!checkedOffList.contains(t))
                    checkedOffList.add(t);
            }
        }

        roomTileList.removeAll(checkedOffList);
        if (roomTileList.size() > 0) {
            random = (int) Math.random() * roomTileList.size();
            Tile firstTile = roomTileList.get(random);
            random = (int) Math.random() * checkedOffList.size();
            Tile secondTile = checkedOffList.get(random);
            this.makePath(firstTile, secondTile);
        } else return;
        this.assertContinuity();
    }

    public void evaluateTile(Tile t) {
        if (checkedOffList.contains(t))
            return;

        try {
            if (!(tileBoard[t.row + 1][t.col] instanceof GenericTile) && !processingList.contains(tileBoard[t.row + 1][t.col])) {
                processingList.add(tileBoard[t.row + 1][t.col]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //e.printStackTrace();
        }

        try {
            if (!(tileBoard[t.row][t.col + 1] instanceof GenericTile) && !processingList.contains(tileBoard[t.row][t.col + 1])) {
                processingList.add(tileBoard[t.row][t.col + 1]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //e.printStackTrace();
        }

        try {
            if (!(tileBoard[t.row][t.col - 1] instanceof GenericTile) && !processingList.contains(tileBoard[t.row][t.col - 1])) {
                processingList.add(tileBoard[t.row][t.col - 1]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //e.printStackTrace();
        }

        try {
            if (!(tileBoard[t.row - 1][t.col] instanceof GenericTile) && !processingList.contains(tileBoard[t.row - 1][t.col])) {
                processingList.add(tileBoard[t.row - 1][t.col]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // e.printStackTrace();
        }
    }

    public ArrayList<PathTile> getPathList() {
        return pathList;
    }
}
