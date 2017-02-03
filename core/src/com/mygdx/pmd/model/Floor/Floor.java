package com.mygdx.pmd.model.Floor;

import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.*;

/**
 * Created by Cameron on 1/24/2017.
 */
public class Floor {

    public Tile[][] tileBoard;
    public Controller controller;
    public Array<StaticEntity> staticEntities;

    public Floor(Controller controller){
        this.controller = controller;
        tileBoard = new Tile[Constants.tileBoardRows][Constants.tileBoardCols];

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

    /**
     * @return Returns an unoccupied room tile
     */
    public Tile chooseUnoccupiedTile() {
        int randRow = PRandomInt.random(0, tileBoard.length - 1);
        int randCol = PRandomInt.random(0, tileBoard[0].length - 1);

        Tile chosenTile = tileBoard[randRow][randCol];

        if (chosenTile instanceof RoomTile && !chosenTile.hasDynamicEntity()) {
            return tileBoard[randRow][randCol];
        } else return chooseUnoccupiedTile();
    }
}
