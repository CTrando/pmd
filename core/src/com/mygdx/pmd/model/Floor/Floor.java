package com.mygdx.pmd.model.Floor;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 1/24/2017.
 */
public class Floor {

    public Tile[][] tileBoard;
    public Controller controller;
    public Array<StaticEntity> staticEntities;

    public Floor(Controller controller){
        this.controller = controller;
        tileBoard = new Tile[DungeonScreen.windowRows][DungeonScreen.windowCols];

        staticEntities = new Array<StaticEntity>();
    }

    /**
     * Keep track of the items on the floor
     */
    public void addEntity(Entity entity){
        if(entity instanceof StaticEntity){
            staticEntities.add((StaticEntity) entity);
        }
    }

    /**
     * clear everything necessary
     */
    public void clear(){
        staticEntities.clear();
    }
}
