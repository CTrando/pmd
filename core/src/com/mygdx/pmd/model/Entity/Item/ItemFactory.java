package com.mygdx.pmd.model.Entity.Item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.*;
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
        JsonReader reader = new JsonReader();
        JsonValue items =reader.parse(Gdx.files.internal("utils/ItemStorage.json"));

        for(JsonValue wItem: items){
            int minNum = wItem.getInt("minNum");
            int maxNum = wItem.getInt("maxNum");
            int numItems = MathUtils.random(minNum, maxNum);

            for(int i = 0; i< numItems; i++){
                Tile rand;
                if(wItem.getBoolean("unOccupied")){
                    rand = floor.chooseUnoccupiedTile();
                } else rand = floor.chooseRandomTile();

                String classifier = wItem.name();

                Item item;

                if(classifier.equals("berry")){
                    item = new Berry(rand);
                } else if(classifier.equals("orb")){
                    item = new Orb(rand);
                } else item = new Apple(rand);

                rand.addEntity(item);
                rand.floor.addItem(item);
            }
        }
    }
}
