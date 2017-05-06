package com.mygdx.pmd.model.Entity.Item;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.Item.Berry;
import com.mygdx.pmd.model.Entity.Item.Item;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/22/2016.
 */
public class ItemFactory {

    public static int maxBerries = 20;
    public static int minBerries = 10;

    public static int maxOrbs = 5;
    public static int minOrbs = 1;

    public static int maxApples = 4;
    public static int minApples = 1;

    public static void placeItems(Floor floor){
        int numBerries = MathUtils.random(minBerries, maxBerries);
        int numOrbs = MathUtils.random(minOrbs, maxOrbs);
        int numApples = MathUtils.random(minApples, maxApples);

        for(int i = 0; i< numBerries; i++) {
            Tile rand = floor.chooseUnoccupiedTile();
            Item item = new Berry(rand);

            rand.addEntity(item);
            rand.floor.addItem(item);
        }

        for(int j = 0; j< numOrbs; j++) {
            Tile rand = floor.chooseUnoccupiedTile();
            Item item = new Orb(rand);

            rand.addEntity(item);
            rand.floor.addItem(item);
        }

        for(int k = 0; k< numApples; k++) {
            Tile rand = floor.chooseUnoccupiedTile();
            Item item = new Apple(rand);

            rand.addEntity(item);
            rand.floor.addItem(item);
        }
    }
}
