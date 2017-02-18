package com.mygdx.pmd.model.Spawner;

import com.mygdx.pmd.interfaces.TurnBaseable;
import com.mygdx.pmd.enumerations.Action;
import com.mygdx.pmd.enumerations.Turn;
import com.mygdx.pmd.model.Entity.*;
import com.mygdx.pmd.model.Floor.*;
import com.mygdx.pmd.model.Tile.Tile;

/**
 * Created by Cameron on 12/21/2016.
 */
public class Spawner extends Entity implements TurnBaseable {

    public Spawner(Floor floor) {
        super(floor, 0, 0);
        this.setTurnState(Turn.COMPLETE);
        setActionState(Action.IDLE);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void setTurnState(Turn turnState) {
        this.turnState = turnState;
    }

    @Override
    public Turn getTurnState() {
        return turnState;
    }
}
