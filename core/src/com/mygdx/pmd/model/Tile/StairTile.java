package com.mygdx.pmd.model.Tile;


import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Floor.Floor;

/**
 * Created by Cameron on 8/15/2016.
 */


public class StairTile extends Tile{
    public StairTile(int r, int c, Floor floor) {
        super(r, c, floor, "STAIR");
        this.isWalkable = true;
        this.sprite = PMD.sprites.get("stairtilesprite");
    }

    @Override
    public void playEvents(DynamicEntity dEntity) {
        if (dEntity instanceof PokemonPlayer) {
            floor.nextFloor();
            Dialogs.showOKDialog(floor.getController().screen.getStage(), "",String.format("You have made it to floor %d", Controller.floorCount));
        }
    }
}
