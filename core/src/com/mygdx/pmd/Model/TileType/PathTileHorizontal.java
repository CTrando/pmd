package com.mygdx.pmd.Model.TileType;


import com.mygdx.pmd.Model.FloorComponent.Floor;
import com.mygdx.pmd.Screen.DungeonScreen;

/**
 * Created by Cameron on 7/15/2016.
 */
public class PathTileHorizontal extends PathTile {

    public PathTileHorizontal(Floor floor, int row, int col) {
        super(row, col, floor, "PATHHOR");
        this.sprite = DungeonScreen.sprites.get("hpathtilesprite");
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
