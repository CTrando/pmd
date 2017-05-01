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

        Array<Tile> path = new Array<Tile>();
        path.add(entity.getComponent(PositionComponent.class).getCurrentTile());
    }

    public void bfs(Tile tile) {
        Array<Tile> newTiles = new Array<Tile>();
        //not while because I want it to run once
        for (Array<Tile> path : paths) {
            Tile lastTile = path.get(path.size - 1);
            int row = lastTile.row;
            int col = lastTile.col;

            if (!lastTile.visited) {
                lastTile.visited = true;
                newTiles.add(tileBoard[row + 1][col]);
                newTiles.add(tileBoard[row - 1][col]);
                newTiles.add(tileBoard[row][col + 1]);
                newTiles.add(tileBoard[row][col - 1]);
            }
        }
    }
}
