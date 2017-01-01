package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 12/22/2016.
 */
public class Berry extends Item {
    public Berry(Tile tile) {
        super(tile);
        this.currentSprite = PMD.sprites.get("berrysprite");
    }

    @Override
    public void playEvents(DynamicEntity receiver){
        receiver.setHp(receiver.getHp()+20);
        //breaks code complete's demeters principle
        receiver.controller.controllerScreen.time+=100;
    }
}
