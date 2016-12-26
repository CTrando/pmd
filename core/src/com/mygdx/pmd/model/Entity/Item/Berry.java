package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/22/2016.
 */
public class Berry extends Item {
    public Berry(Tile tile) {
        super(tile);
        this.currentSprite = PMD.sprites.get("doortilesprite");
    }
}
