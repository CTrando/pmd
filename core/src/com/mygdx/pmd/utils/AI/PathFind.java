package com.mygdx.pmd.utils.AI;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 11/11/2016.
 */
public abstract class PathFind {
    public Entity entity;
    public Tile[][] tileBoard;

    Array<Tile> openNodeList;
    Array<Tile> closedNodeList;
    Array<Tile> solutionNodeList;

    Tile currentTile;

    public PathFind(Entity entity){
        this.entity = entity;
        this.tileBoard = this.entity.tileBoard;
        this.currentTile = entity.currentTile;
        this.solutionNodeList = new Array<Tile>();
    }

    public Array<Tile> pathFind(Tile tile) throws PathFindFailureException{
        return null;
    }
}
