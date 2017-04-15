package com.mygdx.pmd.model.Entity;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/23/2016.
 *
 *
 * Object of this class designed to be placed within tiles
 * They do not currentMove
 * All items must extend this class
 */
public abstract class StaticEntity extends Entity{
    public StaticEntity(Floor floor, int x, int y) {
        super(floor, x, y);
    }

    @Override
    public void runLogic() {

    }
}
