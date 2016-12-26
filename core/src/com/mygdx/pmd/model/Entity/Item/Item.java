package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Entity.StaticEntity;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/22/2016.
 */
public abstract class Item extends StaticEntity{

    public Item(Tile tile) {
        super(tile.controller, tile.x, tile.y);
    }

    @Override
    public void registerObservers() {

    }

}
