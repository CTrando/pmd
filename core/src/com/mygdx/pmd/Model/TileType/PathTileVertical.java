package com.mygdx.pmd.Model.TileType;


import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 7/15/2016.
 */
public class PathTileVertical extends PathTile {

    public PathTileVertical(Floor floor, int row, int col) {
        super(row, col, floor, "PATHVERT");
        this.sprite = DungeonScreen.sprites.get("vpathtilesprite");
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
