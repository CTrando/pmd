package com.mygdx.pmd.model.Tile;


import com.mygdx.pmd.PMD;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Floor.Floor;

/**
 * Created by Cameron on 8/15/2016.
 */


public class StairTile extends Tile{
    public StairTile(int r, int c, Floor floor) {
        super(r, c, floor, "STAIR");
        this.isWalkable = true;
        rc.setSprite(PMD.sprites.get("stairtilesprite"));
    }

    @Override
    public void playEvents(Entity entity) {
        if (entity instanceof PokemonPlayer) {
            floor.nextFloor();
        }
    }

    @Override
    public void runLogic() {

    }
}
