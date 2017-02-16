package com.mygdx.pmd.model.Spawner;

import com.mygdx.pmd.interfaces.TurnBased;
import com.mygdx.pmd.controller.Controller;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Entity.DynamicEntity;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/21/2016.
 */
public class Spawner extends DynamicEntity implements TurnBased {

    public Spawner(Floor floor) {
        super(floor);
        this.setTurnState(Turn.COMPLETE);
        isTurnBased = true;
        setActionState(Action.IDLE);
    }

    @Override
    public void registerObservers() {

    }

    @Override
    public boolean isLegalToMoveTo(Tile tile) {
        return false;
    }

    @Override
    public void randomizeLocation() {

    }

    @Override
    public void dispose() {

    }
}
