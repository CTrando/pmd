package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 12/22/2016.
 */
public abstract class Item extends StaticEntity{
    PositionComponent pc;
    RenderComponent rc;

    protected Item(Tile tile) {
        super(tile.floor, tile.x, tile.y);
        this.rc = new RenderComponent(this);
        this.pc = getComponent(PositionComponent.class);
        components.put(RenderComponent.class, rc);
    }

    public void playEvents(Entity receiver){
    }
}
