/*
package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import PokemonPlayer;

*/
/**
 * Created by Cameron on 8/15/2016.
 *//*

public class StairTile extends Tile{
    public StairTile(Floor floor, int r, int c) {
        super(r, c, floor, "STAIR");
        this.isWalkable = true;
        this.sprite = PMD.sprites.get("stairtilesprite");
    }

    @Override
    public boolean isLegal() {
        return false;
    }

    @Override
    public void playEvents() {
        if (this.containsEntityType(PokemonPlayer.class)) {
            floor.getFloorGenerator().generateFloor();
            floor.getFloorGenerator().controller.randomizeAllPokemonLocation();
        }
    }
}
*/
