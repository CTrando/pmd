package com.mygdx.pmd.utils.AI;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 4/30/2017.
 */
public class BFS extends PathFind {
    private Array<Array<Tile>> paths;
    private Tile[][] tileBoard;

    public BFS(Entity entity) {
        super(entity);
        this.tileBoard = entity.tileBoard;
        this.paths = new Array<Array<Tile>>();
    }

    public Array<Tile> bfs(Tile destTile) {
        boolean hasHit = false;
        paths.clear();
        resetTiles(tileBoard);
        //TODO reset paths on new floor
        Array<Tile> initPath = new Array<Tile>();
        initPath.add(entity.getComponent(PositionComponent.class).getCurrentTile());
        paths.add(initPath);

        while (!hasHit) {
            Array<Array<Tile>> newPaths = new Array<Array<Tile>>();
            Array<Array<Tile>> rmPaths = new Array<Array<Tile>>();
            //not while because I want it to run once
            for (Array<Tile> path : paths) {
                Tile lastTile = path.get(path.size - 1);
                int row = lastTile.row;
                int col = lastTile.col;

                if (lastTile.equals(destTile)) {
                    hasHit = true;
                    System.out.println("FOUND YOU");
                    path.removeIndex(0);
                    return path;
                }

                if (!lastTile.visited && lastTile.isLegalToMoveTo(entity)) {
                    lastTile.visited = true;

                    Tile tile = tileBoard[row + 1][col];
                    Array<Tile> newPath = new Array<Tile>(path);
                    newPath.add(tile);
                    newPaths.add(newPath);

                    tile = tileBoard[row - 1][col];
                    newPath = new Array<Tile>(path);
                    newPath.add(tile);
                    newPaths.add(newPath);

                    tile = tileBoard[row][col + 1];
                    newPath = new Array<Tile>(path);
                    newPath.add(tile);
                    newPaths.add(newPath);

                    tile = tileBoard[row][col - 1];
                    newPath = new Array<Tile>(path);
                    newPath.add(tile);
                    newPaths.add(newPath);
                } else {
                    rmPaths.add(path);
                }
            }
            paths.removeAll(rmPaths, true);
            paths.addAll(newPaths);
        }
        return null;
    }

    private void resetTiles(Tile[][] tileBoard){
        for(int i = 0; i< tileBoard.length; i++){
            for(int j = 0; j< tileBoard[0].length; j++){
                tileBoard[i][j].visited = false;
            }
        }
    }
}
