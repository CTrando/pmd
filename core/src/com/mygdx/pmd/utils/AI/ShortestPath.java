package com.mygdx.pmd.utils.AI;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.Tile;


/**
 * Created by Cameron on 10/30/2016.
 */
public class ShortestPath extends PathFind {

    public ShortestPath(Entity entity) {
        super(entity);
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
            openNodeList.add(pc.getCurrentTile());

            while (currentTile != destination) {
                currentTile = openNodeList.get(0);
                this.evaluateTile(currentTile, destination);
            }

            pc.getCurrentTile().setParent(null);
            Tile backTrack = destination;

            while (backTrack.getParent() != null) {
                backTrack = backTrack.getParent();
                solutionNodeList.insert(0, backTrack);
            }
            solutionNodeList.removeValue(pc.getCurrentTile(), true);
            return solutionNodeList;
        } catch(NullPointerException e){
            throw new PathFindFailureException("Null somewhere in here");
        }
    }

    private void resetLists() {
        openNodeList.clear();
        closedNodeList.clear();
        solutionNodeList.clear();
    }

    private void evaluateTile(Tile tile, Tile dest) {
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
