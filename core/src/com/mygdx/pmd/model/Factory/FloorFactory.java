package com.mygdx.pmd.model.Factory;


import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Tile.GenericTile;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 12/11/2016.
 */
public class FloorFactory {
    Controller controller;
    Tile[][] tileBoard;

    public FloorFactory(Controller controller){
        this.controller = controller;
    }

    public Tile[][] createFloor(){
        tileBoard = new Tile[DungeonScreen.windowRows][DungeonScreen.windowCols];
        for(int i = 0; i< tileBoard.length; i++){
            for(int j = 0; j< tileBoard[0].length; j++){
                tileBoard[i][j] = new GenericTile(i, j, controller);
            }
        }
        return tileBoard;
    }

}
