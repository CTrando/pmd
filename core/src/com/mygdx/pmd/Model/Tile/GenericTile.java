package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Factory.FloorFactory;

import static com.mygdx.pmd.screens.DungeonScreen.windowCols;
import static com.mygdx.pmd.screens.DungeonScreen.windowRows;

/**
 * Created by Cameron on 7/3/2016.
 */
public class GenericTile extends Tile {

    public GenericTile(int row, int col, FloorFactory floorFactory) {
        super(row, col, floorFactory, "GENERIO");
        this.isWalkable = true;
        this.sprite = PMD.sprites.get("generictilesprite");
    }

    @Override
    public boolean isLegal() {
        int row = this.row;
        int col = this.col;

        boolean down = false;
        boolean up = false;
        boolean right = false;
        boolean left = false;

        if (row - 1 > 0) {
            if ((tileBoard[row - 1][col] instanceof RoomTile)) {
                up = true;
            }
        }

        if (col - 1 > 0) {
            if ((tileBoard[row][col - 1] instanceof RoomTile)) {
                left = true;
            }
        }

        if (col + 1 < windowCols) {
            if ((tileBoard[row][col + 1] instanceof RoomTile)) {
                right = true;
            }
        }

        if (row + 1 < windowRows) {
            if ((tileBoard[row + 1][col] instanceof RoomTile)) {
                down = true;
            }
        }

        if (right && down && up && left) {
            return false;
        } else return true;
    }
}
