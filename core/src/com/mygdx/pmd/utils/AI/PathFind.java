package com.mygdx.pmd.utils.AI;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Exceptions.PathFindFailureException;
import com.mygdx.pmd.Model.Tile.Tile;

/**
 * Created by Cameron on 11/11/2016.
 */
public interface PathFind {
    Array<Tile> pathFind(Tile tile) throws PathFindFailureException;
}
