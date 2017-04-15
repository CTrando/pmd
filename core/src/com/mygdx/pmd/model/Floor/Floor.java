package com.mygdx.pmd.model.Floor;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.interfaces.Movable;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Item.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.screens.DungeonScreen;
import com.mygdx.pmd.utils.*;

/**
 * Created by Cameron on 1/24/2017.
 */
public class Floor extends Entity {

    public Tile[][] tileBoard;
    private Array<Item> items;
    private Controller controller;

    public Floor(Controller controller){
        super();
        this.controller = controller;
        tileBoard = new Tile[Constants.tileBoardRows][Constants.tileBoardCols];

        items = new Array<Item>();
    }

    /**
     * Keep track of the items on the floor
     */
    public void addEntity(Entity entity){
        controller.toBeAdded(entity);
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){
        items.removeValue(item, true);
    }

    public void removeEntity(Entity entity){
        controller.toBeRemoved(entity);
    }

    /**
     * clear everything necessary
     */
    public void clear(){
        items.clear();
    }

    /**
     * Recursive method
     * @return Returns an unoccupied room tile
     */
    public Tile chooseUnoccupiedTile() {
        int randRow = MathLogic.random(0, tileBoard.length - 1);
        int randCol = MathLogic.random(0, tileBoard[0].length - 1);

        Tile chosenTile = tileBoard[randRow][randCol];

        if (chosenTile instanceof RoomTile && !chosenTile.hasEntityOfType(Movable.class)) {
            return tileBoard[randRow][randCol];
        } else return chooseUnoccupiedTile();
    }

    @Override
    public void render(SpriteBatch batch){
        for (int i = 0; i < tileBoard.length; i++) {
            for (int j = 0; j < tileBoard[0].length; j++) {
                Tile tile = tileBoard[i][j];
                tile.render(batch);
                //drawing strings like this is very costly performance wise and causes stuttering
                //bFont.draw(batch, tile.spriteValue+"", tile.x + 5, tile.y+25/2);
            }
        }
    }

    @Override
    public void runLogic() {

    }

    public int getNumEntities() {
        return getDynamicEntities().size;
    }

    public DynamicEntity getPlayer() {
        return controller.pokemonPlayer;
    }

    public DungeonScreen getScreen(){
        return controller.screen;
    }

    public Controller getController(){
        return controller;
    }

    public void nextFloor() {
        controller.nextFloor();
    }

    public Array<Item> getItems(){
        return items;
    }

    public Array<DynamicEntity> getDynamicEntities(){
        return controller.dEntities;
    }

    @Override
    public String toString() {
        return "this is the current floor";
    }
}
