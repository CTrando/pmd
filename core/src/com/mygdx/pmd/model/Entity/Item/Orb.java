package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.enumerations.*;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.instructions.*;

/**
 * Created by Cameron on 4/14/2017.
 */
public class Orb extends Item {

    public Orb(Tile tile) {
        super(tile);
        rc.setSprite(PMD.sprites.get("orbsprite"));
    }

    @Override
    public void playEvents(Entity receiver) {
        super.playEvents(receiver);
        if(receiver instanceof Pokemon) {
            Pokemon pokemon = (Pokemon) receiver;
            pokemon.dc.setDirection(Direction.down);
            pokemon.instructions.add(new AnimateInstruction(pokemon, "teleport"));
            pokemon.instructions.add(new RandomizeLocationInstruction(pokemon));
            pokemon.tc.setTurnState(Turn.COMPLETE);

            this.shouldBeDestroyed = true;
        }
    }

    @Override
    public String toString() {
        return "Orb at " + pc.getCurrentTile().toString();
    }
}
