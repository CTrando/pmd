package com.mygdx.pmd.utils.AI;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.Model.TileType.Tile;

/**
 * Created by Cameron on 11/11/2016.
 */
public interface PathFind {
    Array<Tile> pathFind(Tile tile);
}
