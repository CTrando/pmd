package com.mygdx.pmd.model.Entity.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pmd.model.Entity.Entity;
import com.mygdx.pmd.model.Entity.StaticEntity;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.model.components.*;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 12/22/2016.
 */
public abstract class Item extends StaticEntity{
    PositionComponent pc;
    protected Item(Tile tile) {
        super(tile.floor, tile.x, tile.y);
    }

    public void playEvents(Entity receiver){
    }
}
