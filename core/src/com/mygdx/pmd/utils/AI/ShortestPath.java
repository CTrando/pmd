package com.mygdx.pmd.utils.AI;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Exceptions.PathFindFailureException;
import com.mygdx.pmd.Model.Tile.Tile;
import com.mygdx.pmd.Model.Entity.Entity;

import java.nio.file.Path;

/**
 * Created by Cameron on 10/30/2016.
 */
public class ShortestPath implements PathFind {
    public Entity parent;
    public Tile[][] tileBoard;

    Array<Tile> openNodeList;
    Array<Tile> closedNodeList;
    Array<Tile> solutionNodeList;

    Tile currentTile;

    public ShortestPath(Entity entity, Tile[][] tileBoard) {
        this.parent = entity;
        this.tileBoard = tileBoard;
    }

    @Override
    public Array<Tile> pathFind(Tile tile) throws PathFindFailureException{
        return this.findShortestPath(tile);
    }

    public Array<Tile> findShortestPath(Tile destination) throws PathFindFailureException {
        try {
            currentTile = null;
            Tile.resetTileArrayParents(tileBoard);

            this.resetLists();
            openNodeList.add(parent.currentTile);

            while (currentTile != destination) {
                currentTile = openNodeList.get(0); //TODO Index out of bounds exception occuring here which means that parent.currentTile = null
                this.evaluateTile(currentTile, destination);
            }

            parent.currentTile.setParent(null);
            Tile backTrack = destination;

            while (backTrack.getParent() != null) {
                backTrack = backTrack.getParent();
                solutionNodeList.insert(0, backTrack);
            }
            solutionNodeList.removeValue(parent.currentTile, false);
            return solutionNodeList;
        } catch(NullPointerException e){
            throw new PathFindFailureException("Null somewhere in here");
        }
    }

    public void resetLists() {
        openNodeList = new Array<Tile>();
        closedNodeList = new Array<Tile>();
        solutionNodeList = new Array<Tile>();
    }

    public void evaluateTile(Tile tile, Tile dest) {
        if (tile == dest) {
            openNodeList = new Array<Tile>();
            closedNodeList.add(tile);
            return;
        }

        try {
            if (tileBoard[tile.row + 1][tile.col].isWalkable && !openNodeList.contains(tileBoard[tile.row + 1][tile.col], false)) {
                tileBoard[tile.row + 1][tile.col].setParent(tile);
                openNodeList.add(tileBoard[tile.row + 1][tile.col]);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.row - 1][tile.col].isWalkable && !openNodeList.contains(tileBoard[tile.row - 1][tile.col], false)) {
                openNodeList.add(tileBoard[tile.row - 1][tile.col]);
                tileBoard[tile.row - 1][tile.col].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.row][tile.col + 1].isWalkable && !openNodeList.contains(tileBoard[tile.row][tile.col + 1], false)) {
                openNodeList.add(tileBoard[tile.row][tile.col + 1]);
                tileBoard[tile.row][tile.col + 1].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        try {
            if (tileBoard[tile.row][tile.col - 1].isWalkable && !openNodeList.contains(tileBoard[tile.row][tile.col - 1], false)) {
                openNodeList.add(tileBoard[tile.row][tile.col - 1]);
                tileBoard[tile.row][tile.col - 1].setParent(tile);
            }
        } catch (ArrayIndexOutOfBoundsException s) {
        }

        openNodeList.removeValue(tile, false);
        if (!closedNodeList.contains(tile, false))
            closedNodeList.add(tile);
    }
}