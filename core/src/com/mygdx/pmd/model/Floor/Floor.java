package com.mygdx.pmd.model.Floor;

import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 1/24/2017.
 */
public class Floor {

    public Tile[][] tileBoard;
    public Controller controller;

    public Floor(Controller controller){
        this.controller = controller;
        tileBoard = new Tile[DungeonScreen.windowRows][DungeonScreen.windowCols];
    }

}
