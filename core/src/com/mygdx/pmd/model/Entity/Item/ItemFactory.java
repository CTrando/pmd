package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.Item.Berry;
import com.mygdx.pmd.model.Entity.Item.Item;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/22/2016.
 */
public class ItemFactory {

    public static void placeItems(Floor floor){
        for(int i = 0; i< 20; i++) {
            Tile rand = floor.chooseUnoccupiedTile();
            Item item = new Berry(rand);

            rand.addEntity(item);
            rand.floor.addItem(item);
        }
    }
}
