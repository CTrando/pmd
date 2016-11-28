package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.FloorComponent.Floor;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 7/15/2016.
 */
public class PathTileVertical extends PathTile {

    public PathTileVertical(Floor floor, int row, int col) {
        super(row, col, floor, "PATHVERT");
        this.sprite = PMD.sprites.get("vpathtilesprite");
    }

    @Override
    public boolean isLegal() {
        try {
            if ((tileBoard[row][col - 1] instanceof PathTileVertical)) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        try {
            if ((tileBoard[row][col + 1] instanceof PathTileVertical)) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return super.isLegal();
    }
}
