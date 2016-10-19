package com.mygdx.pmd.utils;

import com.mygdx.pmd.Interfaces.Renderable;
import com.mygdx.pmd.Interfaces.Updatable;
import com.mygdx.pmd.Model.TileType.Tile;

/**
 * Created by Cameron on 10/18/2016.
 */
public abstract class Entity implements Renderable, Updatable{
    public int x;
    public int y;

    public boolean equalsTile(Tile t)
    {
        return (t.x == x && t.y == y);
    }
}
