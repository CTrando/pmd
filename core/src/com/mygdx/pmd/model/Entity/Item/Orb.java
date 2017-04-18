package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Tile.*;

/**
 * Created by Cameron on 4/14/2017.
 */
public class Orb extends Item {

    public Orb(Tile tile) {
        super(tile);
        this.currentSprite = PMD.sprites.get("orbsprite");
    }

    @Override
    public void playEvents(Entity receiver) {
        if(receiver instanceof Pokemon) {
            ((Pokemon) receiver).randomizeLocation();
            this.shouldBeDestroyed = true;
        }
    }

    @Override
    public String toString() {
        return "Orb at " + pc.getCurrentTile().toString();
    }
}
