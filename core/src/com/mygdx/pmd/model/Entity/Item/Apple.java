package com.mygdx.pmd.model.Entity.Item;

import com.mygdx.pmd.PMD;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Entity.Pokemon.*;
import com.mygdx.pmd.model.Tile.*;
import com.mygdx.pmd.model.components.*;

/**
 * Created by Cameron on 5/5/2017.
 */
public class Apple extends Item {
    protected Apple(Tile tile) {
        super(tile);
        rc.setSprite(PMD.sprites.get("applesprite"));
    }

    @Override
    public void playEvents(Entity receiver){
        super.playEvents(receiver);

        if(receiver.hasComponent(CombatComponent.class)) {
            CombatComponent cc = receiver.getComponent(CombatComponent.class);
            cc.setHp(cc.getHp() + 50);
            shouldBeDestroyed = true;
        }
    }

    @Override
    public String toString() {
        return "Apple at " + pc.getCurrentTile().toString();
    }
}
