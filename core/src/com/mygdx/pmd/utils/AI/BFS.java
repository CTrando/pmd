package com.mygdx.pmd.utils.AI;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.exceptions.PathFindFailureException;
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

    @Override
    public Array<Tile> pathFind(Tile destTile) throws PathFindFailureException {
        Array<Tile> retPath = null;
        paths.clear();
        Array<Tile> initPath = new Array<Tile>();
        initPath.add(entity.getComponent(PositionComponent.class).getCurrentTile());
        paths.add(initPath);

        boolean hasFound = false;
        boolean[][] visited = new boolean[tileBoard.length][tileBoard[0].length];

        while (!hasFound) {
            Array<Array<Tile>> newPaths = new Array<Array<Tile>>();
            Array<Array<Tile>> rmPaths = new Array<Array<Tile>>();

            for (Array<Tile> path : paths) {
                Tile lastTile = path.get(path.size - 1);
                int row = lastTile.row;
                int col = lastTile.col;

                if (lastTile.equals(destTile)) {
                    System.out.println("FOUND YOU");
                    path.removeIndex(0);
                    retPath = path;
                    hasFound = true;
                    break;
                }

                if (!visited[lastTile.row][lastTile.col] && lastTile.isLegalToMoveTo(entity)) {
                    visited[lastTile.row][lastTile.col] = true;
                    Tile tile = null;
                    Array<Tile> newPath = null;

                    if(Tile.tileExists(tileBoard, row+1, col)) {
                        tile = tileBoard[row + 1][col];
                        newPath = new Array<Tile>(path);
                        newPath.add(tile);
                        newPaths.add(newPath);
                    }

                    if(Tile.tileExists(tileBoard, row-1, col)) {
                        tile = tileBoard[row - 1][col];
                        newPath = new Array<Tile>(path);
                        newPath.add(tile);
                        newPaths.add(newPath);
                    }

                    if(Tile.tileExists(tileBoard, row, col+1)) {
                        tile = tileBoard[row][col + 1];
                        newPath = new Array<Tile>(path);
                        newPath.add(tile);
                        newPaths.add(newPath);
                    }

                    if(Tile.tileExists(tileBoard, row, col-1)) {
                        tile = tileBoard[row][col - 1];
                        newPath = new Array<Tile>(path);
                        newPath.add(tile);
                        newPaths.add(newPath);
                    }
                } else {
                    rmPaths.add(path);
                }
            }

            paths.removeAll(rmPaths, true);
            paths.addAll(newPaths);
            if(paths.size  == 0){
                throw new PathFindFailureException("BFS FAILED");
            }
        }
        return retPath;
    }
}
