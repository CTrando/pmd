package com.mygdx.pmd.model.Floor;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.*;

/**
 * Created by Cameron on 1/24/2017.
 */
public class Floor extends Entity{

    public Tile[][] tileBoard;
    private Array<StaticEntity> staticEntities;

    public Floor(Controller controller){
        super(controller);
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
     * Recursive method
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

    @Override
    public void registerObservers() {

    }

    @Override
    public void render(SpriteBatch batch){
        for (int i = 0; i < tileBoard.length; i++) {
            for (int j = 0; j < tileBoard[0].length; j++) {
                Tile tile = controller.currentFloor.tileBoard[i][j];
                tile.render(batch);
                //drawing strings like this is very costly performance wise and causes stuttering
                //bFont.draw(batch, tile.spriteValue+"", tile.x + 5, tile.y+25/2);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
