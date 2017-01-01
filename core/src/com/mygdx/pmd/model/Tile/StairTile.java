package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Factory.FloorFactory;

/**
 * Created by Cameron on 8/15/2016.
 */


public class StairTile extends Tile{
    public StairTile(int r, int c, FloorFactory floorFactory) {
        super(r, c, floorFactory, "STAIR");
        this.isWalkable = true;
        this.sprite = PMD.sprites.get("stairtilesprite");
    }

    @Override
    public boolean isLegal() {
        return false;
    }

    @Override
    public void playEvents(DynamicEntity dEntity) {
        if (dEntity instanceof PokemonPlayer) {
            controller.nextFloor();
        }
    }
}
