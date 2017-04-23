package com.mygdx.pmd.utils.AI;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.exceptions.PathFindFailureException;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 11/11/2016.
 */
public abstract class PathFind {
    public Entity entity;

    protected PositionComponent pc;
    protected MoveComponent mc;

    Array<Tile> openNodeList;
    Array<Tile> closedNodeList;
    Array<Tile> solutionNodeList;

    Tile currentTile;
    Tile[][] tileBoard;

    public PathFind(Entity entity){
        this.entity = entity;
        this.tileBoard = entity.tileBoard;
        this.pc = entity.getComponent(PositionComponent.class);
        this.mc = entity.getComponent(MoveComponent.class);

        this.currentTile = pc.getCurrentTile();

        this.solutionNodeList = new Array<Tile>();
        this.openNodeList = new Array<Tile>();
        this.closedNodeList = new Array<Tile>();
    }

    public Array<Tile> pathFind(Tile tile) throws PathFindFailureException{
        return null;
    }
}
