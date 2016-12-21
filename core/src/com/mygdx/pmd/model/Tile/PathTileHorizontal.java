/*
package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;

*/
/**
 * Created by Cameron on 7/15/2016.
 *//*

public class PathTileHorizontal extends PathTile {

    public PathTileHorizontal(Floor floor, int row, int col) {
        super(row, col, floor, "PATHHOR");
        this.sprite = PMD.sprites.get("hpathtilesprite");
    }

    @Override
    public boolean isLegal() {
        if (Tile.tileExists(tileBoard, row - 1, col)) {
            if ((tileBoard[row - 1][col] instanceof PathTileHorizontal)) {
                return false;
            }

        }

        if (Tile.tileExists(tileBoard, row + 1, col)) {
            if ((tileBoard[row + 1][col] instanceof PathTileHorizontal)) {
                return false;
            }
        }

        return super.isLegal();
    }
}
*/
