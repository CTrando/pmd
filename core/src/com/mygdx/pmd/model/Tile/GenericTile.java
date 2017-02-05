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
}
