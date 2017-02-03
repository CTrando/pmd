package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Floor.Floor;
import com.mygdx.pmd.utils.Constants;

/**
 * Created by Cameron on 7/3/2016.
 */
public class GenericTile extends Tile {

    public GenericTile(int row, int col, Floor floor) {
        super(row, col, floor, "GENERIC");
        this.isWalkable = false;
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

        if (col + 1 < Constants.tileBoardCols) {
            if ((tileBoard[row][col + 1] instanceof RoomTile)) {
                right = true;
            }
        }

        if (row + 1 < Constants.tileBoardRows) {
            if ((tileBoard[row + 1][col] instanceof RoomTile)) {
                down = true;
            }
        }

        if (right && down && up && left) {
            return false;
        } else return true;
    }
}
