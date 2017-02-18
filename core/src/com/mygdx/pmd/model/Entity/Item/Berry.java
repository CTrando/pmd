package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.interfaces.Damageable;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.PokemonPlayer;
import com.mygdx.pmd.model.Factory.FloorFactory;
import com.mygdx.pmd.model.Tile.Tile;
import com.mygdx.pmd.screens.DungeonScreen;

/**
 * Created by Cameron on 12/22/2016.
 */
public class Berry extends Item {
    public Berry(Tile tile) {
        super(tile);
        this.currentSprite = PMD.sprites.get("berrysprite");
    }

    @Override
    public void playEvents(Entity receiver){
        if(receiver instanceof Damageable) {
            Damageable damageable = (Damageable) receiver;
            damageable.setHP(damageable.getHP() + 20);

        }
        if (receiver instanceof PokemonPlayer)
            Controller.turns += 10;
    }
}
