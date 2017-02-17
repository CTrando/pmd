package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/22/2016.
 */
public abstract class Item extends StaticEntity{

    public Item(Tile tile) {
        super(tile.floor, tile.x, tile.y);
    }

    @Override
    public void registerObservers() {

    }

    public void playEvents(Entity receiver){
    }

}
