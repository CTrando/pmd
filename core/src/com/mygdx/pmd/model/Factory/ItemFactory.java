package com.mygdx.pmd.model.Factory;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.Item.Berry;
import com.mygdx.pmd.model.Entity.Item.Item;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/22/2016.
 */
public class ItemFactory {

    public static void placeItems(Tile[][] tileBoard){
        for(int i = 0; i< 5; i++) {
            Tile rand = Controller.chooseUnoccupiedTile(tileBoard);
            Item item = new Berry(rand);
            rand.addEntity(item);
            //controller.addEntity(item);
        }
    }

}
